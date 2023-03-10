package io.github.tlh.jmb.mapper;

import io.github.tlh.jmb.common.pojo.MessageBoardInfo;

import java.util.List;

/**
 * @author wuliling Created By 2023-01-29 15:02
 **/
public interface MessageBoardInfoMapper {

    MessageBoardInfo queryById(Integer id);

    List<MessageBoardInfo> listRootMessage();

    List<MessageBoardInfo> listReplyMessage(Integer id);

    void insert(MessageBoardInfo message);

}
