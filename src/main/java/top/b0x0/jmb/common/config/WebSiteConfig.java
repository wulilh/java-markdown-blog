package top.b0x0.jmb.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.b0x0.jmb.common.pojo.GiTalk;

import java.io.File;
import java.nio.file.Path;

/**
 * 网站配置
 *
 * @author wuliling Created By 2023-01-17 9:43
 **/
@Configuration
@ConfigurationProperties("website")
@Data
@Slf4j
public class WebSiteConfig {

    private String markdownDir; //Markdown文件根目录
    private Path markdownPath;
    private String indexDir; //存放about.md 以及md文件元信息
    private Path indexPath;
    private String cacheDir = "cache/md"; //文档html缓存
    private Path cachePath;
    private String about = "about.md";

    private String metaFile = "metaInfo.json";
    private String catalogFile = "catalog.json";
    private String charset = "UTF-8";
    private GiTalk giTalk;

    public WebSiteConfig() {
        File cacheFileDir = new File(cacheDir);
        boolean exists = cacheFileDir.exists();
        if (!exists) {
            boolean mkdir = cacheFileDir.mkdir();
            log.info("Construct create cache-dir result:[{}]", mkdir);
        }
    }

    public void setMarkdownDir(String markdownDir) {
        this.markdownDir = markdownDir;
        this.markdownPath = new File(markdownDir).toPath();
    }

    public void setIndexDir(String indexDir) {
        this.indexDir = indexDir;
        this.indexPath = new File(indexDir).toPath();
    }

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
        File cacheFileDir = new File(cacheDir);
        boolean exists = cacheFileDir.exists();
        if (!exists) {
            boolean mkdir = cacheFileDir.mkdir();
            log.info("setCacheDir create cache-dir result:[{}]", mkdir);
        }
        this.cachePath = cacheFileDir.toPath();

    }
}
