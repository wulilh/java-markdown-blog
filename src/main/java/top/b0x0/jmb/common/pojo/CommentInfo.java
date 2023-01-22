package top.b0x0.jmb.common.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * comment_info
 *
 * @author wuliling Created By 2023-01-22 15:03
 **/
@Data
public class CommentInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String userId;
    private String nickname;
    private String email;
    private String content;
    private Date createTime;
    private Date updateTime;
    private String isValid;

}