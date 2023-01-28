package top.b0x0.jmb.common.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wuliling Created By 2023-01-28 21:52
 **/
@Data
@Accessors(chain = true)
public class Message {
    private Integer id;
    private String userId; //评论者昵称
    private String nickname; //评论者昵称
    private String email; //邮箱
    private String content; //评论内容
    private String avatar; //头像
    private Date createTime;

    private Boolean replyInform;//留言被回复后是否发邮件通知

    private String openid;//评论人的openid，默认为空

    //评论类自关联关系，一个评论下面会有很多个回复
    private List<Message> replyMessages = new ArrayList<>(); //回复
    private Message parentMessage; //评论

    private Integer adminMessage = 0;//评论类型，0为游客评论，1为访客评论，2为管理员评论
}
