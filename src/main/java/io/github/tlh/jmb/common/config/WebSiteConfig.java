package io.github.tlh.jmb.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
@SuppressWarnings("all")
public class WebSiteConfig {

    private String theme = "default"; //主题
    private String markdownDir; //Markdown文件根目录
    private transient Path markdownPath;
    private String indexDir; //存放About.md 以及md文件元信息
    private transient Path indexPath;
    private String cacheDir = "cache/md"; //文档html缓存
    private transient Path cachePath;

    private String about = "About.md";

    private String catalogFile = "catalog.json";
    private String charset = "UTF-8";

    private String author = "wulilinghan";
    private String authorAvatar = "https://i.328888.xyz/2023/02/05/NPvYP.jpeg";

    private String webSiteTittle = "wuliling's 博客";
    private String webSiteDescription = "一个用Java写的Markdown博客";
    private String webSiteLogo = "https://i.328888.xyz/2023/02/05/NPLMJ.png";
    private String webSiteIcon;

    private Footer footer;

    private GiTalk giTalk;

    /**
     * 网易云播放器
     */
    private Boolean pluginPlayer = Boolean.FALSE;
    /**
     * 网易云播放器插件，有两种  netEasy-music ，neteasePlayer
     */
    private String pluginPlayerType = "netEasy-music";

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


    /**
     * Gitalk 是一个基于 GitHub Issue 和 Preact 开发的评论插件。
     * https://gitalk.github.io/
     *
     * @author wuliling Created By 2023-01-17 9:51
     **/
    @Data
    public static class GiTalk {
        private Boolean enable = Boolean.FALSE;
        private String owner;
        private String admin;
        private String repo;
        private String clientId;
        private String secret;
    }

    @Data
    public static class Footer {
        private String icp;
        private String poweredBy = "java-markdown-blog";
        private String poweredByUrl = "https://github.com/wulilinghan/java-markdown-blog";
    }
}
