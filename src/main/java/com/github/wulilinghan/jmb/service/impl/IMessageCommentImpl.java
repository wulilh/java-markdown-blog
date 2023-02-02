package com.github.wulilinghan.jmb.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.wulilinghan.jmb.common.pojo.MessageCommentInfo;
import com.github.wulilinghan.jmb.mapper.MessageCommentInfoMapper;
import com.github.wulilinghan.jmb.service.IMessageComment;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuliling Created By 2023-01-28 21:05
 **/
@Service
@Slf4j
public class IMessageCommentImpl implements IMessageComment {
    @Resource
    MessageCommentInfoMapper messageCommentInfoMapper;

    /**
     * 存放迭代找出的所有子代的集合
     */
    private List<MessageCommentInfo> tmpReplyList = new ArrayList<>();

    @Override
    public PageInfo<MessageCommentInfo> selectByPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<MessageCommentInfo> list = messageCommentInfoMapper.listRootMessage();
        final PageInfo<MessageCommentInfo> infoPageInfo = new PageInfo<>(list);

        List<MessageCommentInfo> messagesView = new ArrayList<>();
        for (MessageCommentInfo message : list) {
            // 获取顶节点下子级回复
            recursively(message);
            // 将所有子级回复放到当前顶节点评论集合中
            recursivelyReplyMessagesToRootMessage(message);
            message.setReplyComments(tmpReplyList);
            tmpReplyList = new ArrayList<>();
            messagesView.add(message);
        }
        infoPageInfo.setList(messagesView);
        return infoPageInfo;
    }

    /**
     * 递归获取回复
     *
     * @param rootMessage 被迭代的对象rootMessage = {MessageCommentInfo@10967} "Collecting data…"
     */
    private void recursively(MessageCommentInfo rootMessage) {
        // 获取当前message 第一层回复
        List<MessageCommentInfo> replyMessages = messageCommentInfoMapper.listReplyMessage(rootMessage.getId());
        if (CollectionUtils.isEmpty(replyMessages)) {
            return;
        }
        for (MessageCommentInfo replyMessage : replyMessages) {
            replyMessage.setParentComment(rootMessage);
            // 递归子级
            recursively(replyMessage);
        }
        rootMessage.setReplyComments(replyMessages);
    }

    private void recursivelyReplyMessagesToRootMessage(MessageCommentInfo message) {
        List<MessageCommentInfo> replyListTmp = message.getReplyComments();
        if (CollectionUtils.isEmpty(replyListTmp)) {
            return;
        }
        tmpReplyList.addAll(replyListTmp);
        for (MessageCommentInfo reply : replyListTmp) {
            recursivelyReplyMessagesToRootMessage(reply);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageCommentInfo saveComment(MessageCommentInfo message) {
        if (message.getParentComment() == null) {
            message.setParentCommentId(null);
            message.setParentComment(null);
        } else if (message.getParentComment().getId() != -1) {
            Integer parentCommentId = message.getParentComment().getId();
            message.setParentCommentId(parentCommentId);
            message.setParentComment(messageCommentInfoMapper.queryById(parentCommentId));
        }
        message.setUserId(IdUtil.getSnowflakeNextIdStr());
        log.info("json comment:[{}]", JSON.toJSONString(message));
        messageCommentInfoMapper.insert(message);
        return message;
    }
}
