package top.b0x0.jmb.common.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author wuliling Created By 2023-01-30 11:03
 **/
@Data
public class UserGithub {

    private Integer id;

    private String githubId;//github账号id
    private String nodeId;//github唯一标识
    private String loginTime;//首次登录博客时间
    private String createdTime;//创建github账号时间
    private String updatedTime;//最后更新github时间
    private String avatar;//github头像
    private String nickname;//github账号名字
    private String publicRepos;//公有仓库数量
    private String subscriptions;//仓库详细信息url
    private String receivedEventsUrl;//操作事件详细信息url
    private String indexUrl;//github用户首页
    private String cip;//ip地址
    private String cid;//地区编号
    private String cname;//所在地

    private Date createTime;
    private Date updateTime;
    private String isValid;
}
