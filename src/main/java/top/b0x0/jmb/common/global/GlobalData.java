package top.b0x0.jmb.common.global;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;
import top.b0x0.jmb.common.pojo.ArticleMetaData;
import top.b0x0.jmb.common.pojo.Catalog;
import top.b0x0.jmb.common.pojo.MetaInfo;
import top.b0x0.jmb.common.pojo.AuthorInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wuliling Created By 2023-01-18 16:38
 **/
public class GlobalData {
    public static String theme = "default";

    public final static String MARKDOWN_SUFFIX = "md";
    public static String CHARSET = "";

    public static String MARKDOWN_DIR = "";
    public static String INDEX_DIR = "";

    public static MetaInfo metaInfo;
    protected transient AuthorInfo authorInfo = new AuthorInfo();

    public static CopyOnWriteArrayList<ArticleMetaData> markdownMetaList = new CopyOnWriteArrayList<>();
    public static ConcurrentHashMap<String, ArticleMetaData> markdownIndex = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Catalog> catalogIndex = new ConcurrentHashMap<>();
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
        return FilenameUtils.getExtension(file.toString()).equals(MARKDOWN_SUFFIX);
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
