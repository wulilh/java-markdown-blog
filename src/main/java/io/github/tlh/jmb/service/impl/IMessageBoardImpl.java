package io.github.tlh.jmb.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.tlh.jmb.mapper.MessageBoardInfoMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import io.github.tlh.jmb.common.pojo.MessageBoardInfo;
import io.github.tlh.jmb.service.IMessageBoard;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuliling Created By 2023-01-28 21:05
 **/
@Service
@Slf4j
public class IMessageBoardImpl implements IMessageBoard {
    @Resource
    MessageBoardInfoMapper messageBoardInfoMapper;

    /**
     * 存放迭代找出的所有子代的集合
     */
    private List<MessageBoardInfo> tmpReplyList = new ArrayList<>();

    @Override
    public PageInfo<MessageBoardInfo> selectByPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<MessageBoardInfo> list = messageBoardInfoMapper.listRootMessage();
        final PageInfo<MessageBoardInfo> infoPageInfo = new PageInfo<>(list);

        List<MessageBoardInfo> messagesView = new ArrayList<>();
        for (MessageBoardInfo message : list) {
            // 获取顶节点下子级回复
            recursively(message);
            // 将所有子级回复放到当前顶节点评论集合中
            recursivelyReplyMessagesToRootMessage(message);
            message.setReplyMessages(tmpReplyList);
            tmpReplyList = new ArrayList<>();
            messagesView.add(message);
        }
        infoPageInfo.setList(messagesView);
        return infoPageInfo;
    }

    /**
     * 递归获取回复
     *
     * @param rootMessage 被迭代的对象rootMessage = {MessageBoardInfo@10967} "Collecting data…"
     */
    private void recursively(MessageBoardInfo rootMessage) {
        // 获取当前message 第一层回复
        List<MessageBoardInfo> replyMessages = messageBoardInfoMapper.listReplyMessage(rootMessage.getId());
        if (CollectionUtils.isEmpty(replyMessages)) {
            return;
        }
        for (MessageBoardInfo replyMessage : replyMessages) {
            replyMessage.setParentMessage(rootMessage);
            // 递归子级
            recursively(replyMessage);
        }
        rootMessage.setReplyMessages(replyMessages);
    }

    private void recursivelyReplyMessagesToRootMessage(MessageBoardInfo message) {
        List<MessageBoardInfo> replyListTmp = message.getReplyMessages();
        if (CollectionUtils.isEmpty(replyListTmp)) {
            return;
        }
        tmpReplyList.addAll(replyListTmp);
        for (MessageBoardInfo reply : replyListTmp) {
            recursivelyReplyMessagesToRootMessage(reply);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageBoardInfo saveMessage(MessageBoardInfo message) {
        Integer parentMessageId = message.getParentMessage().getId();
        if (parentMessageId != -1) {
            message.setParentMessageId(parentMessageId);
            message.setParentMessage(messageBoardInfoMapper.queryById(parentMessageId));
        } else {
            message.setParentMessageId(null);
            message.setParentMessage(null);
        }
        message.setUserId(IdUtil.getSnowflakeNextIdStr());
        log.info("json message:[{}]", JSON.toJSONString(message));
        messageBoardInfoMapper.insert(message);
        return message;
    }
}
