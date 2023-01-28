package top.b0x0.jmb.common.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * comment_reply
 *
 * @author wuliling Created By 2023-01-28 21:03
 **/
@Data
@Accessors(chain = true)
public class CommentReply implements Serializable {

    @Serial
    private static final long serialVersionUID = 7269320839269593558L;

    private Integer id;
    private Integer commentId;
    private String userId;
    private String nickname;
    private String email;
    private String toUserId;
    private String content;
    private Date createTime;
    private Date updateTime;
    private String isValid;

}