package top.b0x0.jmb.mapper;

import top.b0x0.jmb.common.pojo.MessageBoardInfo;

import java.util.List;

public interface MessageBoardInfoMapper {

    MessageBoardInfo queryById(Integer id);

    List<MessageBoardInfo> listRootMessage();

    List<MessageBoardInfo> listReplyMessage(Integer id);

    void insert(MessageBoardInfo message);

    void insertReply(MessageBoardInfo message);
}
