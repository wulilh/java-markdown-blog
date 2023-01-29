package top.b0x0.jmb.service;

import com.github.pagehelper.PageInfo;
import top.b0x0.jmb.common.pojo.MessageBoardInfo;

/**
 * @author wuliling Created By 2023-01-28 21:04
 **/
public interface IMessageBoard {

    PageInfo<MessageBoardInfo> selectByPage(int page, int size);

    MessageBoardInfo saveMessage(MessageBoardInfo message);
}
