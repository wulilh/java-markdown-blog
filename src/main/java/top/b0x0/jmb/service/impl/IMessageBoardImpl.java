package top.b0x0.jmb.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.b0x0.jmb.common.pojo.CommentInfo;
import top.b0x0.jmb.common.pojo.CommentReply;
import top.b0x0.jmb.common.pojo.Message;
import top.b0x0.jmb.mapper.CommentInfoMapper;
import top.b0x0.jmb.service.IMessageBoard;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuliling Created By 2023-01-28 21:05
 **/
@Service
public class IMessageBoardImpl implements IMessageBoard {
    @Resource
    CommentInfoMapper commentInfoMapper;

    @Override
    public PageInfo<Message> selectByPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<CommentInfo> list = commentInfoMapper.list();

        List<Message> messagesView = new ArrayList<>();

        for (CommentInfo commentInfo : list) {
            Message message = new Message().
                    setId(commentInfo.getId())
                    .setCreateTime(commentInfo.getCreateTime())
                    .setNickname(commentInfo.getNickname())
                    .setEmail(commentInfo.getEmail())
                    .setContent(commentInfo.getContent());
            // 获取回复
            recursively(message);
        }
        return new PageInfo<>(messagesView);
    }

    /**
     * 递归获取回复
     *
     * @param rootMessage 被迭代的对象
     */
    private void recursively(Message rootMessage) {
        CommentReply commentReply = new CommentReply();
        commentReply.setCommentId(rootMessage.getId())
                .setNickname(rootMessage.getNickname())
                .setEmail(rootMessage.getEmail())
                .setToUserId(rootMessage.getUserId())
        ;
        // 获取当前message 第一层回复
        List<CommentReply> commentReplies = commentInfoMapper.listReply(commentReply);
        List<Message> replyMessages = new ArrayList<>();
        for (CommentReply reply : commentReplies) {
            Message messageTmp = new Message().
                    setId(reply.getId())
                    .setCreateTime(reply.getCreateTime())
                    .setNickname(reply.getNickname())
                    .setEmail(reply.getEmail())
                    .setContent(reply.getContent())
                    .setParentMessage(rootMessage);
            replyMessages.add(messageTmp);
        }
        rootMessage.setReplyMessages(replyMessages);

        // 递归获取子级回复
        for (CommentReply reply : commentReplies) {
            Message messageTmp = new Message()
                    .setId(reply.getCommentId())
                    .setNickname(reply.getNickname())
                    .setEmail(reply.getEmail())
                    .setUserId(reply.getUserId());
            recursively(messageTmp);
        }
    }
}
