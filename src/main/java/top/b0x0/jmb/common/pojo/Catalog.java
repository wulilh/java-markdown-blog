package top.b0x0.jmb.common.pojo;

import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import top.b0x0.jmb.common.utils.HashCode;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 目录信息
 *
 * @author wuliling Created By 2023-01-17 11:18
 **/
@Data
public class Catalog {

    private String name;//当前目录路径
    private String uri;//当前目录路径
    private String sha256;//文件夹sha256
    private boolean isDirectory;
    private List<Catalog> subCatalogs = new ArrayList<>();//子级目录的文章信息
    private List<ArticleMetaData> articles = new ArrayList<>();//当前目录的文章信息

    public Catalog() {
    }

    public Catalog(String uri) {
        this.uri = uri;
        this.name = FilenameUtils.getName(new File(uri).getName());
    }

    public void addSubCatalog(Catalog catalog) {
        this.subCatalogs.add(catalog);
    }

    public void addArticleMetaInfo(ArticleMetaData articleMetaData) {
        this.articles.add(articleMetaData);
    }

    public List<String> catalogs() {
        List<String> list = new LinkedList<>();
        for (Catalog catalog : subCatalogs) {
            list.add(catalog.getUri());
        }
        return list;
    }

    public int getArticleCount() {
        return articles.size();
    }
}
