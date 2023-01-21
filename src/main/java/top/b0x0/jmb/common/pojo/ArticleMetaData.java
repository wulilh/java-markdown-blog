package top.b0x0.jmb.common.pojo;

import lombok.Data;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 文档信息
 *
 * @author wuliling Created By 2023-01-17 11:07
 **/
@Data
public class ArticleMetaData {

    private String sha256;
    private String absolutePath; //文件绝对路径
    private transient Path path;

    private String articleId;
    private String title; //标题
    private String summary; //摘要
    private String coverImage = "";
    private String categoryName = "";
    private String categoryIcon = "";
    private long creationTime; // 创建时间
    private long lastModifiedTime; // 更新时间
    private long lastAccessTime; // 上次访问时间
    private int views; // 阅读量
    private List<Tag> tags = new ArrayList<>(); //博客与标签的关系为多对多
    private boolean appreciation = false; //赞赏
    private boolean shareStatement = true; //
    private boolean commentabled = false; //留言

    public ArticleMetaData(File file) {
        this.absolutePath = file.getAbsolutePath();
        this.path = file.toPath();
    }

}
