package com.github.wulilinghan.jmb.common.enums;

/**
 * 留言类型
 *
 * @author wuliling Created By 2023-01-29 10:43
 **/
public enum MessageTypeEnum {
    /**
     * 评论类型，0为游客评论，1为访客评论，2为管理员评论,3为GitHub账号评论
     */
    tourist(0, "游客评论"),
    visitor(1, "访客评论"),
    admin(2, "管理员评论"),
    github(3, "GitHub账号评论"),
    ;

    private Integer type;
    private String desc;

    MessageTypeEnum(Integer type, String desc) {
    }

    public Integer getType() {
        return type;
    }


    public String getDesc() {
        return desc;
    }


}
