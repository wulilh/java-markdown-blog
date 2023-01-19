package top.b0x0.jmb.common.pojo;

import lombok.Data;

import java.io.File;
import java.nio.file.Path;

/**
 * 文档信息
 *
 * @author wuliling Created By 2023-01-17 11:07
 **/
@Data
public class ArticleMetaData {

    private long hash;
    private String sha256;
    private String absolutePath; //文件绝对路径
    private transient Path path;

    private String title; //标题
    private String summary; //摘要
    private long creationTime; // 创建时间
    private long lastModifiedTime; // 更新时间
    private long lastAccessTime; // 上次访问时间

    public ArticleMetaData(File file) {
        this.absolutePath = file.getAbsolutePath();
        this.path = file.toPath();
    }

    public ArticleMetaData(File file, long parentHash) {
        this.absolutePath = file.getName();
        this.path = file.toPath();
    }

}
