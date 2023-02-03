package com.github.wulilinghan.jmb.common.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;

/**
 * message_comment_info
 *
 * @author wuliling Created By 2023-02-02 22:52
 **/
@Data
@Accessors(chain = true)
public class MessageCommentInfo {
    private Integer id;
    private Integer parentCommentId;
    private String articleId; //文章id
    private String userId; //评论者昵称
    private String nickname; //评论者昵称
    private String email; //邮箱
    private String content; //评论内容
    private String avatar; //头像
    private Date createTime;
    private Date updateTime;
    private String isValid;

    private String toUserId;
    private String toNickname;
    private String toEmail;

    private Boolean replyInform;//留言被回复后是否发邮件通知

    private ArticleMetaData articleMetaData;

    private String openid;//评论人的openid，默认为空

    //一个评论下面会有很多个回复
    private List<MessageCommentInfo> replyComments = new ArrayList<>(); //回复
    private MessageCommentInfo parentComment; //评论

    /**
     * @see com.github.wulilinghan.jmb.common.enums.CommentTypeEnum
     */
    private Integer adminComment = 0;

    public void appendReplyMessage(Collection<MessageCommentInfo> messages) {
        this.replyComments.addAll(messages);
    }

    @Override
    public String toString() {
        return "MessageCommentInfo{" +
                "id=" + id +
                ", parentCommentId=" + parentCommentId +
                ", userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isValid='" + isValid + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", toNickname='" + toNickname + '\'' +
                ", toEmail='" + toEmail + '\'' +
                ", replyInform=" + replyInform +
                ", articleMetaData=" + articleMetaData +
                ", openid='" + openid + '\'' +
                ", parentComment=" + parentComment +
                ", adminComment=" + adminComment +
                '}';
    }
}
