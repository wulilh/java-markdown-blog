package top.b0x0.jmb.init;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.hankcs.hanlp.HanLP;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import top.b0x0.jmb.common.config.WebSiteConfig;
import top.b0x0.jmb.common.global.GlobalData;
import top.b0x0.jmb.common.pojo.ArticleMetaData;
import top.b0x0.jmb.common.pojo.Catalog;
import top.b0x0.jmb.common.pojo.GiTalk;
import top.b0x0.jmb.common.pojo.MetaInfo;
import top.b0x0.jmb.common.utils.OSUtils;
import top.b0x0.jmb.component.Cost;
import top.b0x0.jmb.component.FileListenerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
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
public class InitWebSite extends GlobalData implements ApplicationRunner {

    @Resource
    private FileListenerFactory fileListenerFactory;
    @Resource
    private WebSiteConfig webSiteConfig;
    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;

    @PostConstruct
    public void init() {
        MARKDOWN_DIR = webSiteConfig.getMarkdownPath().toFile().getPath();
        INDEX_DIR = webSiteConfig.getIndexPath().toFile().getPath();
        CHARSET = webSiteConfig.getCharset();
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("web site start init....");
        try (final Cost cost = new Cost("web-site-init")) {
            metaInfo = loadCatalog();
            markdownMetaList = sortMarkDownFiles(metaInfo.getCatalog());
            aboutFile = new File(webSiteConfig.getIndexDir() + OSUtils.fileSeparator() + webSiteConfig.getAbout());
            setThymeleafGlobalStaticVariables();
            startFileMonitor();
        } catch (Exception e) {
            log.error("System init error", e);
            log.error("System exit！");
            System.exit(-1);
        }
        log.info("web site end init....");
    }

    private void startFileMonitor() throws Exception {
        FileAlterationMonitor markdownDirMonitor = fileListenerFactory.getMonitor(MARKDOWN_DIR);
        markdownDirMonitor.start();
        FileAlterationMonitor indexDirMonitor = fileListenerFactory.getMonitor(INDEX_DIR);
        indexDirMonitor.start();
    }

    /**
     * 设置thymeleaf全局变量
     */
    private void setThymeleafGlobalStaticVariables() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("websiteName", "测试博客");
        configMap.put("websiteDescription", "一个用java写的Markdown博客");
        configMap.put("websiteLogo", "https://cn.gravatar.com/userimage/176875695/30e6f4fb8ae8b9eef75989ac24806248.png");
        configMap.put("websiteIcon", "https://cn.gravatar.com/userimage/176875695/30e6f4fb8ae8b9eef75989ac24806248.png");
        configMap.put("avatar", "https://cn.gravatar.com/userimage/176875695/30e6f4fb8ae8b9eef75989ac24806248.png");
        configMap.put("email", "1902325071@qq.com");
        configMap.put("qq", "1902325071");
        configMap.put("name", "李瑕");
        configMap.put("footerAbout", "");
        configMap.put("footerICP", "AE8666");
        configMap.put("footerCopyRight", "@NewNew");
        configMap.put("footerPoweredBy", "WULILINGHAN");
        configMap.put("footerPoweredByURL", "https://github.com/wulilinghan/java-markdown-blog");
        thymeleafViewResolver.setStaticVariables(
                new HashMap<>() {
                    {
                        put("giTalk", new GiTalk());
                        put("newblogs", markdownMetaList.subList(0, 5));
                        put("configurations", configMap);
                    }
                }
        );
    }

    /**
     * 按创建时间排序所有文章
     *
     * @param catalog 根目录
     * @return List<MetaData> 所有文章列表
     */
    public CopyOnWriteArrayList<ArticleMetaData> sortMarkDownFiles(final Catalog catalog) {
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
        if (!CollectionUtils.isEmpty(subCatalogs)) {
            for (Catalog subCatalog : subCatalogs) {
                getArticleMetaInfoFromCatalog(subCatalog, result);
            }
        }
    }

    /**
     * 获取本地 markdown 文档目录信息
     */
    private MetaInfo loadCatalog() throws Exception {
        String markdownDir = webSiteConfig.getMarkdownDir();
        String indexDir = webSiteConfig.getIndexDir();
        log.info("web-site-config: markdown dir[{}] , index dir[{}] ", markdownDir, indexDir);
        File markDownDirFile = new File(markdownDir);
        if (!markDownDirFile.isDirectory()) {
            log.error("config markDown-dir[{}] is not directory", markdownDir);
            throw new RuntimeException("markDown-dir is not directory");
        }
        File indexDirFile = new File(indexDir);
        if (!indexDirFile.isDirectory()) {
            log.error("config index-dir[{}] is not directory", indexDir);
            throw new RuntimeException("index-dir is not directory");
        }
        // 获取文档目录元数据信息
        MetaInfo metaInfo = new MetaInfo();
        String metaJsonFilePath = indexDirFile.getAbsolutePath() + OSUtils.fileSeparator() + webSiteConfig.getMetaFile();
        // 文档根目录
        Catalog rootCatalog = new Catalog(markDownDirFile.getPath());
        // 递归获取子目录信息
        recursiveCatalog(markDownDirFile, rootCatalog);
        metaInfo.setCatalog(rootCatalog);

        //------------------------------------------------
        List<ArticleMetaData> rootArticles = rootCatalog.getArticles();
        for (ArticleMetaData ra : rootArticles) {
            markdownIndex.put(ra.getArticleId(), ra);
        }
        catalogIndex.put(rootCatalog.getSha256(), rootCatalog);
        for (Catalog subCatalog : rootCatalog.getSubCatalogs()) {
            catalogIndex.put(subCatalog.getSha256(), subCatalog);
        }
        //------------------------------------------------

        String jsonString = JSON.toJSONString(metaInfo);
        FileUtils.writeStringToFile(new File(metaJsonFilePath), jsonString, CHARSET);
        return metaInfo;
    }

    /**
     * 读取本地MetaInfo文件
     *
     * @param metaJsonFilePath D:\website\index\viewers.json
     * @return /
     */
    private MetaInfo loadLocalMetaInfo(String metaJsonFilePath) {
        MetaInfo metaInfo = null;
        try {
            File oldMetaInfoFile = new File(metaJsonFilePath);
            if (oldMetaInfoFile.exists()) {
                String metaJsonString = FileUtils.readFileToString(oldMetaInfoFile, Charset.forName(CHARSET));
                if (StringUtils.hasText(metaJsonString)) {
                    metaInfo = JSON.parseObject(metaJsonString, MetaInfo.class);
                }
            }
        } catch (Exception e) {
            log.error("load local MetaInfo error", e);
        }
        return metaInfo;
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
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            catalog.setSha256(DigestUtils.sha256Hex(fileInputStream));
        } catch (Exception ignored) {
            catalog.setSha256(IdUtil.simpleUUID());
        }
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
                try (FileInputStream fileInputStream = new FileInputStream(subFile)) {
                    subCatalog.setSha256(DigestUtils.sha256Hex(fileInputStream));
                } catch (Exception ignored) {
                    subCatalog.setSha256(IdUtil.simpleUUID());
                }

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
        metaInfo.setArticleId(IdUtil.simpleUUID()); // articleId
        metaInfo.setTitle(FilenameUtils.getBaseName(file.toString()));//标题
//        metaInfo.setSummary(MarkDownHandler.mdSimpleToHtml(getSummary(file)));//摘要
        String summary = HanLP.getSummary(getContent(file), 100);
        metaInfo.setSummary(summary);//摘要
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            metaInfo.setSha256(DigestUtils.sha256Hex(fileInputStream)); // 文件sha256

            Path path = file.toPath();
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            metaInfo.setCreationTime(attr.creationTime().toMillis()); // 创建时间
            metaInfo.setLastModifiedTime(attr.lastModifiedTime().toMillis()); // 更新时间
            metaInfo.setLastAccessTime(attr.lastAccessTime().toMillis()); // 上次访问时间
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        metaInfo.setCoverImage("https://www.2008php.com/2018_Website_appreciate/2018-11-05/20181105120350bcNnmbcNnm.jpg");
        return metaInfo;
    }


}
