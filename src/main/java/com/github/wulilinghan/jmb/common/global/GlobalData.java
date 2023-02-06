package com.github.wulilinghan.jmb.common.global;

import com.github.wulilinghan.jmb.common.exception.ArticleNotFoundException;
import com.github.wulilinghan.jmb.common.pojo.ArticleMetaData;
import com.github.wulilinghan.jmb.common.pojo.Catalog;
import com.github.wulilinghan.jmb.common.pojo.Tag;
import com.github.wulilinghan.jmb.common.utils.CommonUtils;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuliling Created By 2023-01-18 16:38
 **/
@Slf4j
public class GlobalData {
    //public static String theme = "yummy-jekyll";
    public static String theme = "simple";
//    public static String theme = "default";

    public final static String MARKDOWN_SUFFIX = "md";
    public static String CHARSET = "";

    public static String MARKDOWN_DIR_STR = "";
    public static File MARKDOWN_DIR_FILE;
    public static String INDEX_DIR_STR = "";
    public static File INDEX_DIR_FILE;

    public static Catalog catalog;

    /**
     * 文档目录
     */
    public static ConcurrentHashMap<String, Catalog> catalogIndex = new ConcurrentHashMap<>();

    /**
     * 文档集合
     */
    public static CopyOnWriteArrayList<ArticleMetaData> articleMetaList = new CopyOnWriteArrayList<>();
    /**
     * 文档索引
     */
    public static ConcurrentHashMap<String, ArticleMetaData> articleMetaIndex = new ConcurrentHashMap<>();

    /**
     * tag 自增id
     */
    public static AtomicInteger tagIncId = new AtomicInteger(0);
    /**
     * 标签集合
     */
    public static CopyOnWriteArrayList<Tag> tagList = new CopyOnWriteArrayList<>();
    /**
     * 标签索引
     */
    public static ConcurrentHashMap<Integer, Tag> tagIndex = new ConcurrentHashMap<>();

    public static File aboutFile;

    public static String getContent(File file) throws ArticleNotFoundException {
        List<String> lines = getArticleLinesContent(file);
        return String.join("\n", lines);
    }

    public static String getSummary(File file) throws ArticleNotFoundException {
        List<String> lines = getArticleLinesContent(file);
        List<String> rs = new ArrayList<>();
        int index = 0;
        int summaryEnd = 4;
        for (String s : lines) {
            if (index >= summaryEnd) {
                break;
            }
            if (StringUtils.hasText(s)) {
                index++;
                rs.add(s);
            }
        }
        return String.join("\n", rs);
    }

    public boolean isMarkDownFile(File file) {
        return file != null && !file.isDirectory() && FilenameUtils.getExtension(file.toString()).equals(MARKDOWN_SUFFIX);
    }

    public static List<String> getArticleLinesContent(String path) throws ArticleNotFoundException {
        return getArticleLinesContent(new File(path));
    }

    /**
     * 逐行读取文档内容
     *
     * @param file /
     * @return /
     */
    public static List<String> getArticleLinesContent(File file) throws ArticleNotFoundException {
        List<String> list = new ArrayList<>();
        try {
            list = FileUtils.readLines(file, StringUtils.hasText(CHARSET) ? CHARSET : "utf-8");
        } catch (Exception e) {
            throw new ArticleNotFoundException(null).setMessage(CommonUtils.getMarkdownRelativePath(file) + "文件可能已被删除");
        }
        return list;
    }

    /**
     * 设置文章元信息
     *
     * @param file /
     * @return /
     */
    public static ArticleMetaData getArticleMetaInfo(final File file) {
        // 文档元信息
        ArticleMetaData metaInfo = new ArticleMetaData(file);
        // 根据文件名生成唯一id
        metaInfo.setArticleId(CommonUtils.markdownUniId(MARKDOWN_DIR_FILE, file));
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
