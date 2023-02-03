package com.github.wulilinghan.jmb.mapper;

import com.github.wulilinghan.jmb.common.pojo.MessageCommentInfo;

import java.util.List;

/**
 * @author wuliling Created By 2023-01-29 15:02
 **/
public interface MessageCommentInfoMapper {

    MessageCommentInfo queryById(Integer id);

    List<MessageCommentInfo> listRootComment(String articleId);

    List<MessageCommentInfo> listReplyComment(String articleId, Integer id);

    void insert(MessageCommentInfo message);

}
