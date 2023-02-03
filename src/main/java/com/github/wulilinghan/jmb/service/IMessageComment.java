package com.github.wulilinghan.jmb.service;

import com.github.pagehelper.PageInfo;
import com.github.wulilinghan.jmb.common.pojo.MessageCommentInfo;

/**
 * @author wuliling Created By 2023-01-28 21:04
 **/
public interface IMessageComment {

    PageInfo<MessageCommentInfo> listCommentsByArticleId(String articleId, int page, int size);

    MessageCommentInfo saveComment(MessageCommentInfo message);
}
