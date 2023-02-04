package com.github.wulilinghan.jmb.init;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.github.wulilinghan.jmb.common.config.WebSiteConfig;
import com.github.wulilinghan.jmb.common.exception.ArticleNotFoundException;
import com.github.wulilinghan.jmb.common.global.GlobalData;
import com.github.wulilinghan.jmb.common.pojo.ArticleMetaData;
import com.github.wulilinghan.jmb.common.pojo.Catalog;
import com.github.wulilinghan.jmb.common.pojo.Tag;
import com.github.wulilinghan.jmb.common.utils.IdUtils;
import com.github.wulilinghan.jmb.common.utils.OSUtils;
import com.github.wulilinghan.jmb.component.Cost;
import com.github.wulilinghan.jmb.component.FileListenerFactory;
import com.hankcs.hanlp.HanLP;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 初始化
 *
 * @author wuliling Created By 2023-01-17 11:12
 **/
@Component
@Slf4j
@Order(1)
public class InitWebSite extends GlobalData implements ApplicationRunner {

    private transient WebSiteConfig.GiTalk giTalk;


    @Resource
    private Environment environment;

    @Resource
    private FileListenerFactory fileListenerFactory;
    @Resource
    private WebSiteConfig webSiteConfig;
    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;

    private static String serverPort;
    private static String serverIp;

    @PostConstruct
    public void init() {
        MARKDOWN_DIR_FILE = webSiteConfig.getMarkdownPath().toFile();
        MARKDOWN_DIR_STR = MARKDOWN_DIR_FILE.getPath();

        INDEX_DIR_FILE = webSiteConfig.getIndexPath().toFile();
        INDEX_DIR_STR = INDEX_DIR_FILE.getPath();

        CHARSET = webSiteConfig.getCharset();

        serverPort = getPort();
        serverIp = getIp();
    }

    public String getPort() {
        return environment.getProperty("server.port");
    }

    public String getIp() {
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private void startFileMonitor() throws Exception {
        FileAlterationMonitor markdownDirMonitor = fileListenerFactory.getMonitor(MARKDOWN_DIR_STR);
        markdownDirMonitor.start();
        FileAlterationMonitor indexDirMonitor = fileListenerFactory.getMonitor(INDEX_DIR_STR);
        indexDirMonitor.start();
    }

    /**
     * 设置thymeleaf全局变量
     */
    private void setThymeleafGlobalStaticVariables() {
        thymeleafViewResolver.setStaticVariables(
                new HashMap<>(4) {
                    {
                        put("giTalk", giTalk);
                        put("newblogs", articleMetaList.subList(0, 3));
                        put("webSiteConfig", webSiteConfig);
                        put("footer", webSiteConfig.getFooter());
                    }
                }
        );
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("web site start init....");
        try (final Cost cost = new Cost("web-site-init")) {
            catalog = loadCatalog();
            loadTag();
            articleMetaList = sortArticle(catalog);
            aboutFile = new File(webSiteConfig.getIndexDir() + OSUtils.fileSeparator() + webSiteConfig.getAbout());
            setThymeleafGlobalStaticVariables();
            startFileMonitor();
        } catch (Exception e) {
            log.error("System init error", e);
            log.error("System exit！");
            System.exit(-1);
        }
        log.info("web site end init....");
        log.info("\n-------------------------------------"
                + "\n读取本地Markdown文档完成."
                + "\nhttp://" + serverIp + ":" + serverPort
                + "\n-------------------------------------");
    }

    private void loadTag() {
        if (MARKDOWN_DIR_FILE == null || !MARKDOWN_DIR_FILE.isDirectory()) {
            return;
        }
        File[] files = MARKDOWN_DIR_FILE.listFiles();
        for (File file : files) {
            recursiveTag(file);
        }
    }

    /**
     * 判断当前文件夹下是否有Markdown文件
     *
     * @param file /
     * @return /
     */
    private boolean isHaveMdFile(File file) {
        return file != null && file.isDirectory() && Arrays.stream(file.listFiles()).anyMatch(this::isMarkDownFile);
    }

    private void recursiveTag(File file) {
        if (file.isDirectory()) {
            // 当前文件夹下没有Markdown文件，则不新增标签
            if (!isHaveMdFile(file)) {
                return;
            }
            String tagName = FilenameUtils.getName(file.getPath());
            Tag tag = new Tag(tagName);
            // tag索引新增
            tagIndex.put(tag.getId(), tag);
            tagList.add(tag);

            File[] subFiles = file.listFiles();
            if (subFiles == null || subFiles.length == 0) {
                return;
            }
            for (File subFile : subFiles) {
                if (isMarkDownFile(subFile)) {
                    ArticleMetaData articleMetaInfo = getArticleMetaInfo(subFile);
                    tag.addArticle(articleMetaInfo);
                }
                if (subFile.isDirectory()) {
                    recursiveTag(subFile);
                }
            }
        }
    }

    /**
     * 按创建时间排序所有文章
     *
     * @param catalog 根目录
     * @return List<MetaData> 所有文章列表
     */
    public CopyOnWriteArrayList<ArticleMetaData> sortArticle(final Catalog catalog) {
        List<ArticleMetaData> result = new CopyOnWriteArrayList<>();
        // 递归获取目录的 ArticleMetaInfo
        getArticleMetaInfoFromCatalog(catalog, result);
        // 按创建时间排序所有文章
        return result.stream()
                .sorted(Comparator.comparingLong(ArticleMetaData::getCreationTime).reversed())
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    private void getArticleMetaInfoFromCatalog(Catalog catalog, List<ArticleMetaData> result) {
        if (catalog == null) {
            return;
        }
        result.addAll(catalog.getArticles());

        List<Catalog> subCatalogs = catalog.getSubCatalogs();
        if (CollectionUtils.isEmpty(subCatalogs)) {
            return;
        }

        for (Catalog subCatalog : subCatalogs) {
            getArticleMetaInfoFromCatalog(subCatalog, result);
        }
    }

    /**
     * 获取本地 markdown 文档目录信息
     */
    private Catalog loadCatalog() throws Exception {
        String markdownDir = webSiteConfig.getMarkdownDir();
        String indexDir = webSiteConfig.getIndexDir();
        log.info("website-config: markdown dir[{}] , index dir[{}] ", markdownDir, indexDir);
        File markDownDirFile = new File(markdownDir);
        if (!markDownDirFile.isDirectory()) {
            throw new RuntimeException("website.markdown-dir[{" + markdownDir + "}] not set or is not exist or not directory");
        }
        File indexDirFile = new File(indexDir);
        if (!indexDirFile.isDirectory()) {
            throw new RuntimeException("website.index-dir[{" + indexDir + "}] not set or is not exist or not directory");
        }
        // 获取文档目录元数据信息
        String catalogJsonFilePath = indexDirFile.getAbsolutePath() + OSUtils.fileSeparator() + webSiteConfig.getCatalogFile();
        // 文档根目录
        Catalog rootCatalog = new Catalog(markDownDirFile.getPath());
        // 递归获取子目录信息
        recursiveCatalog(markDownDirFile, rootCatalog);

        //------------------------------------------------
        List<ArticleMetaData> rootArticles = rootCatalog.getArticles();
        for (ArticleMetaData ra : rootArticles) {
            articleMetaIndex.put(ra.getArticleId(), ra);
        }
        catalogIndex.put(rootCatalog.getId(), rootCatalog);
        for (Catalog subCatalog : rootCatalog.getSubCatalogs()) {
            catalogIndex.put(subCatalog.getId(), subCatalog);
        }
        //------------------------------------------------

        // 写入目录信息文件
        String catalogJsonString = JSON.toJSONString(rootCatalog);
        FileUtils.writeStringToFile(new File(catalogJsonFilePath), catalogJsonString, CHARSET);
        catalog = rootCatalog;
        return rootCatalog;
    }

    /**
     * 递归获取 目录 及目录下 markdown 文件信息
     *
     * @param file    /
     * @param catalog /
     */
    private void recursiveCatalog(File file, Catalog catalog) {
        if (!file.isDirectory()) {
            catalog.setName(FilenameUtils.getName(file.getName()));
            catalog.setDirectory(false);
            catalog.addArticleMetaInfo(getArticleMetaInfo(file));
            return;
        }
        catalog.setName(FilenameUtils.getName(file.getName()));
        catalog.setDirectory(true);
        catalog.setId(IdUtils.fileUniId(MARKDOWN_DIR_FILE, file));

        // 子目录及子目录文件
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File subFile : files) {
            if (subFile.isFile() && isMarkDownFile(subFile)) {
                catalog.addArticleMetaInfo(getArticleMetaInfo(subFile));
            }

            if (subFile.isDirectory()) {
                // 创建子目录
                Catalog subCatalog = new Catalog(subFile.toString());
                subCatalog.setName(FilenameUtils.getName(subFile.getName()));
                subCatalog.setDirectory(true);
                subCatalog.setId(IdUtil.getSnowflakeNextIdStr());

                // 递归子目录
//                recursiveCatalog(subFile, subCatalog);

                // 获取子目录下所有md文件。这样子 rootCatalog 下只有一层subCatalogs，subCatalogs中Catalog没有子级文件夹
                Collection<File> subFiles = FileUtils.listFiles(subFile, new String[]{"md"}, true);
                for (File sf : subFiles) {
                    subCatalog.addArticleMetaInfo(getArticleMetaInfo(sf));
                }

                // 给上级目录设置子目录信息
                catalog.addSubCatalog(subCatalog);
            }
        }
    }

    /**
     * 设置文章元信息
     *
     * @param file /
     * @return /
     */
    private ArticleMetaData getArticleMetaInfo(final File file) {
        // 文档元信息
        ArticleMetaData metaInfo = new ArticleMetaData(file);
        // 根据文件名生成唯一id
        metaInfo.setArticleId(IdUtils.fileUniId(MARKDOWN_DIR_FILE, file));
        //标题
        metaInfo.setTitle(FilenameUtils.getBaseName(file.toString()));
        //摘要
//        metaInfo.setSummary(MarkDownHandler.mdSimpleToHtml(getSummary(file)));
        String summary = "";
        try {
            if (StringUtils.hasText(getContent(file))) {
                summary = HanLP.getSummary(getContent(file), 100);
            }
        } catch (ArticleNotFoundException e) {
            e.printStackTrace();
        }
        metaInfo.setSummary(summary);
        try {
            Path path = file.toPath();
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            // 创建时间
            metaInfo.setCreationTime(attr.creationTime().toMillis());
            // 更新时间
            metaInfo.setLastModifiedTime(attr.lastModifiedTime().toMillis());
            // 上次访问时间
            metaInfo.setLastAccessTime(attr.lastAccessTime().toMillis());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        // https://source.unsplash.com/random/1270x720
        // https://source.unsplash.com/random/1720x720
        // https://picsum.photos/seed/picsum/1720/720
        metaInfo.setCoverImage("https://source.unsplash.com/random/1720x720");
        return metaInfo;
    }


}
