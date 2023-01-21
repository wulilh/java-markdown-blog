package top.b0x0.jmb.common.pojo;

import lombok.Data;

/**
 * Gitalk 是一个基于 GitHub Issue 和 Preact 开发的评论插件。
 * https://gitalk.github.io/
 *
 * @author wuliling Created By 2023-01-17 9:51
 **/
@Data
public class GiTalk {
    private boolean enable = false;
    private String owner;
    private String admin;
    private String repo;
    private String clientId;
    private String secret;
}