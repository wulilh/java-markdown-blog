package io.github.tlh.jmb.common.pojo;

import io.github.tlh.jmb.common.enums.MessageTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;

/**
 * message_board_info
 *
 * @author wuliling Created By 2023-01-28 21:52
 **/
@Data
@Accessors(chain = true)
public class MessageBoardInfo {
    private Integer id;
    private Integer parentMessageId;
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

    private String openid;//评论人的openid，默认为空

    //一个评论下面会有很多个回复
    private List<MessageBoardInfo> replyMessages = new ArrayList<>(); //回复
    private MessageBoardInfo parentMessage; //评论

    /**
     * @see MessageTypeEnum
     */
    private Integer adminMessage = 0;

    public void appendReplyMessage(Collection<MessageBoardInfo> messages) {
        this.replyMessages.addAll(messages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageBoardInfo that = (MessageBoardInfo) o;
        return Objects.equals(id, that.id) && Objects.equals(parentMessageId, that.parentMessageId) && Objects.equals(userId, that.userId) && Objects.equals(nickname, that.nickname) && Objects.equals(email, that.email) && Objects.equals(content, that.content) && Objects.equals(avatar, that.avatar) && Objects.equals(createTime, that.createTime) && Objects.equals(updateTime, that.updateTime) && Objects.equals(isValid, that.isValid) && Objects.equals(toUserId, that.toUserId) && Objects.equals(toNickname, that.toNickname) && Objects.equals(toEmail, that.toEmail) && Objects.equals(replyInform, that.replyInform) && Objects.equals(openid, that.openid) && Objects.equals(parentMessage, that.parentMessage) && Objects.equals(adminMessage, that.adminMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentMessageId, userId, nickname, email, content, avatar, createTime, updateTime, isValid, toUserId, toNickname, toEmail, replyInform, openid, parentMessage, adminMessage);
    }
}
