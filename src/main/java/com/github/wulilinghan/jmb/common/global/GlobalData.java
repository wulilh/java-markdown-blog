package com.github.wulilinghan.jmb.common.global;

import com.github.wulilinghan.jmb.common.pojo.ArticleMetaData;
import com.github.wulilinghan.jmb.common.pojo.Catalog;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;
import com.github.wulilinghan.jmb.common.pojo.Tag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuliling Created By 2023-01-18 16:38
 **/
public class GlobalData {
    //public static String theme = "yummy-jekyll";
    //public static String theme = "amaze";
    public static String theme = "default";

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

    public static String getContent(File file) {
        List<String> lines = getArticleLinesContent(file);
        return String.join("\n", lines);
    }

    public static String getSummary(File file) {
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

    public static List<String> getArticleLinesContent(String path) {
        return getArticleLinesContent(new File(path));
    }

    /**
     * 逐行读取文档内容
     *
     * @param file /
     * @return /
     */
    public static List<String> getArticleLinesContent(File file) {
        List<String> list = new ArrayList<>();
        try {
            list = FileUtils.readLines(file, StringUtils.hasText(CHARSET) ? CHARSET : "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
