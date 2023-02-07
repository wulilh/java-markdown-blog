package io.github.tlh.jmb.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuliling Created By 2023-01-30 14:19
 **/
@Configuration
@ConfigurationProperties("github")
@Data
@Slf4j
public class GithubConfig {

    private String clientId;
    private String clientSecret;
    private String callback;
    private String tokenUrl;
    private String userInfoUrl;

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = "https://github.com/login/oauth/access_token?client_id=" + this.clientId
                + "&client_secret=" + this.clientSecret + "&code=%s&redirect_uri=" + this.callback;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = "https://api.github.com/user?access_token=%s";
    }
}
