package io.github.tlh.jmb.test;

import com.alibaba.fastjson.JSON;
import io.github.tlh.jmb.TestBase;
import io.github.tlh.jmb.common.pojo.ArticleMetaData;
import io.github.tlh.jmb.common.pojo.Catalog;
import io.github.tlh.jmb.common.utils.JacksonUtils;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author wuliling Created By 2023-01-17 15:04
 **/
public class FileTest extends TestBase {

    @Test
    public void test_path() throws IOException {
        String rootPath = "D:\\work-space\\github\\assets\\markdown-articles";
        String s = rootPath + "\\IntelliJIDEA使用\\插件篇.md";
        String rootAbsolutePath = new File(rootPath).getAbsolutePath();
        System.out.println("absolutePath = " + rootAbsolutePath);

        File file = new File(s);
        String fileAbsolutePath = file.getAbsolutePath();
        System.out.println("file = " + fileAbsolutePath);

        String replace = fileAbsolutePath.replace(rootAbsolutePath, "");
        System.out.println("replace = " + replace);
    }

    @Test
    public void test_r() throws IOException {
        String s = "cache";
        File file = new File(s);
        System.out.println("file = " + file.getAbsolutePath());

        boolean exists = file.exists();
        System.out.println("exists = " + exists);
        if (!exists) {
            boolean mkdir = file.mkdir();
            System.out.println("mkdir = " + mkdir);
        }
//        FileUtils.touch(file);
    }

    @Test
    public void test() {
        String s = "jdbc:sqlite:dbs/jmb.db";
        if (!StringUtils.hasLength(s) || !(s.split(":").length == 3)) {
            throw new RuntimeException(s + " sqlite url error");
        }
        String dataSourceUrl = s.split(":")[2];
        String absolutePath = new File(dataSourceUrl).getAbsolutePath();
        System.out.println("absolutePath = " + absolutePath);
    }

    @Test
    public void path() throws IOException {
        File file = new File("D:\\website\\articles");
        String baseName = FilenameUtils.getBaseName(file.toString());
        System.out.println("baseName = " + baseName);
    }

    @Test
    public void test_readFile() throws Exception {
        File oldMetaInfoFile = new File("D:\\website\\articles\\metaInfo.json");

        String metaJsonString = FileUtils.readFileToString(oldMetaInfoFile, Charset.forName("utf-8"));
//        if (StringUtils.hasText(metaJsonString)) {
//            MetaInfo metaInfo = JacksonUtils.json2pojo(metaJsonString, MetaInfo.class);
//            System.out.println("metaInfo = " + metaInfo);
//        }
        Catalog metaInfo = JSON.parseObject(metaJsonString, Catalog.class);
        System.out.println("metaInfo = " + JSON.toJSONString(metaInfo));
    }


    @Test
    public void test_writeFile() throws Exception {
        File file = new File("D:\\website\\articles\\metaInfo.json");
        Catalog catalog = new Catalog(file.getAbsolutePath());
        String obj2json = JacksonUtils.obj2json(catalog);
        FileUtils.writeStringToFile(file, obj2json, "utf-8");
    }

    @Test
    public void test_listFiles() throws IOException {
        File markDownDirFile = new File("D:\\website\\articles");

        Collection<File> markdownFiles1 = FileUtils.listFiles(markDownDirFile, new SuffixFileFilter("md"), DirectoryFileFilter.INSTANCE);
        Collection<File> markdownFiles2 = FileUtils.listFiles(markDownDirFile, null, true);
    }

    @Test
    public void test_recursiveCatalog() throws IOException {
        File markDownDirFile = new File("D:\\website\\articles");
        if (markDownDirFile.isDirectory()) {
            throw new RuntimeException("AAA");
        }
        Catalog rootCatalog = new Catalog(markDownDirFile.getAbsolutePath());
        recursiveCatalog(markDownDirFile, rootCatalog);
//        Collection<File> markdownFiles = FileUtils.listFiles(markDownDirFile, null, true);
//        System.out.println("markdownFiles = " + markdownFiles);
    }

    private void recursiveCatalog(File file, Catalog catalog) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File subFile : files) {
                if (subFile.isFile() && isMarkDownFile(subFile)) {
                    catalog.addArticleMetaInfo(genArticleMetaInfo(file));
                } else if (subFile.isDirectory()) {
                    Catalog subCatalog = new Catalog(FilenameUtils.getBaseName(file.toString()));
                    catalog.addSubCatalog(subCatalog);
                    recursiveCatalog(subFile, subCatalog);
                }
            }
        } else {
            catalog.addArticleMetaInfo(genArticleMetaInfo(file));
        }
    }

    private ArticleMetaData genArticleMetaInfo(final File file) {
        ArticleMetaData metaData = new ArticleMetaData(file);
        metaData.setTitle(FilenameUtils.getBaseName(file.toString()));
        metaData.setSummary(FilenameUtils.getBaseName(file.toString()));
        metaData.setLastModifiedTime(file.lastModified());
        return metaData;
    }

    private boolean isMarkDownFile(File file) {
        return FilenameUtils.getExtension(file.toString()).equals("md");
    }

    @Test
    public void test_checksumCRC32() throws IOException {
        File file = new File("D:\\website\\articles\\Spring.md");
        final String absolutePath = file.getAbsolutePath();
        System.out.println("absolutePath = " + absolutePath); //D:\website\articles\Spring.md
        long checksumCRC32 = FileUtils.checksumCRC32(file);
        System.out.println("checksumCRC32 = " + checksumCRC32); //1772097516
    }

    @Test
    public void test_readFileToString() throws IOException {
        File file = new File("D:\\website\\articles" + "/metaInfo.json");
        String fileToString = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
        System.out.println("fileToString = " + fileToString);
    }

    @Test
    public void test_() throws IOException {
        File file = new File("D:\\website\\articles" + "/metaInfo.json");
        List<String> contents = Files.readLines(file, Charset.forName("UTF-8"));
        for (String content : contents) {
            System.out.println("content = " + content);
        }
    }

    @Test
    public void test_readAttributes() throws IOException {
        final File file = new File("D:\\website\\articles\\Mysql数据备份.md");
        Path path = file.toPath();
        BasicFileAttributes attr = java.nio.file.Files.readAttributes(path, BasicFileAttributes.class);
        System.out.println("attr = " + attr.toString());

        Properties properties = System.getProperties();
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {

        }
    }

    @Test
    public void test_fileCreatedTime() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final File file = new File("D:\\website\\articles\\生活\\边城.md");
        BasicFileAttributes attr = null;
        try {
            Path path = file.toPath();
            attr = java.nio.file.Files.readAttributes(path, BasicFileAttributes.class);
            // 创建时间
            Instant instant = attr.creationTime().toInstant();
            String ct = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault()).format(instant);
            System.out.println("ct = " + ct);

            // 更新时间
//        Instant instant = attr.lastModifiedTime().toInstant();
            // 上次访问时间
//        Instant instant = attr.lastAccessTime().toInstant();

            final long cl = attr.creationTime().toMillis();
            final String creationTime = sdf.format(cl);
            System.out.println("creationTime = " + creationTime);
            final long upl = attr.lastModifiedTime().toMillis();
            final String lastModifiedTime = sdf.format(upl);
            System.out.println("lastModifiedTime = " + lastModifiedTime);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
