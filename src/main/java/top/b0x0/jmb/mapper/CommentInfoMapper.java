package top.b0x0.jmb.mapper;

import top.b0x0.jmb.common.pojo.CommentInfo;
import top.b0x0.jmb.common.pojo.CommentReply;

import java.util.List;

public interface CommentInfoMapper {

    List<CommentInfo> list();

    List<CommentReply> listReply(CommentReply reply);
}
