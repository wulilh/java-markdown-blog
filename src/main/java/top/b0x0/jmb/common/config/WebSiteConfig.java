package top.b0x0.jmb.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.b0x0.jmb.common.pojo.GiTalk;
import top.b0x0.jmb.common.pojo.Page;

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
public class WebSiteConfig {

    private String markdownDir; //Markdown文件根目录
    private Path markdownPath;
    private String indexDir; //存放about.md 以及md文件元信息
    private Path indexPath;
    private String about = "about.md";


    private String metaFile = "metaInfo.json";
    private String charset = "UTF-8";
    private Page page;
    private String hostName;
    private GiTalk giTalk;

    public void setMarkdownDir(String markdownDir) {
        this.markdownDir = markdownDir;
        this.markdownPath = new File(markdownDir).toPath();
    }

    public void setIndexDir(String indexDir) {
        this.indexDir = indexDir;
        this.indexPath = new File(indexDir).toPath();
    }
}
