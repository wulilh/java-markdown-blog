package top.b0x0.jmb.service;

import com.github.pagehelper.PageInfo;
import top.b0x0.jmb.common.pojo.Message;

/**
 * @author wuliling Created By 2023-01-28 21:04
 **/
public interface IMessageBoard {

    PageInfo<Message> selectByPage(int page, int size);
}
