package top.b0x0.jmb.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
        List<CommentInfo> list = commentInfoMapper.listComment();

        List<Message> messagesView = new ArrayList<>();

        for (CommentInfo commentInfo : list) {
            Message message = new Message().
                    setId(commentInfo.getId())
                    .setCreateTime(commentInfo.getCreateTime())
                    .setUserId(commentInfo.getUserId())
                    .setNickname(commentInfo.getNickname())
                    .setEmail(commentInfo.getEmail())
                    .setContent(commentInfo.getContent());
            // 获取回复
            recursively(message);
            // 将回复放到当前子级评论集合中
            recursivelyToRootMessage(message);
            messagesView.add(message);
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
//                .setNickname(rootMessage.getNickname())
//                .setEmail(rootMessage.getEmail())

//                .setToUserId(rootMessage.getUserId()) // 留言板展示暂不支持树形结构  只支持两层
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
                    .setParentMessage(rootMessage)
                    .setToNickname(reply.getToNickname())
                    .setToEmail(reply.getToEmail());
            replyMessages.add(messageTmp);
        }
        rootMessage.setReplyMessages(replyMessages);

        // 留言板展示暂不支持树形结构  只支持两层
        // 递归获取子级回复
/*        for (CommentReply reply : commentReplies) {
            Message messageTmp = new Message()
                    .setId(reply.getCommentId())
//                    .setNickname(reply.getNickname())
//                    .setEmail(reply.getEmail())
                    .setUserId(reply.getUserId());
            recursively(messageTmp);
        }*/
    }

    private void recursivelyToRootMessage(Message message) {
        if (CollectionUtils.isEmpty(message.getReplyMessages())) {
            return;
        }
        List<Message> replyListTmp = message.getReplyMessages();
        for (Message reply : replyListTmp) {
            List<Message> replyMessages = reply.getReplyMessages();
            if (CollectionUtils.isEmpty(replyMessages)) {
                return;
            }
            message.appendReplyMessage(replyMessages);
            recursivelyToRootMessage(reply);
        }
    }
}
