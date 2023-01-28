package top.b0x0.jmb.controller;

import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import top.b0x0.jmb.common.global.GlobalData;
import top.b0x0.jmb.common.pojo.Message;
import top.b0x0.jmb.common.pojo.PageQueryBaseDto;
import top.b0x0.jmb.service.IMessageBoard;

/**
 * 留言板
 *
 * @author wuliling Created By 2023-01-22 20:05
 **/
@Controller
@RequestMapping("/")
public class MessageBoardController {
    public static String theme = GlobalData.theme;

    @Value("${comment.default.avatar}")
    private String avatar;

    @Resource
    IMessageBoard messageBoard;

    @GetMapping("messages")
    public ModelAndView comments(PageQueryBaseDto query) {
        ModelAndView modelAndView = new ModelAndView("theme/" + theme + "/message_board");
        PageInfo<Message> commentInfoPageInfo = messageBoard.selectByPage(query.getCurrPage(), query.getPageSize());
        modelAndView.addObject("messages", commentInfoPageInfo);
        boolean a = commentInfoPageInfo.isIsFirstPage(); //判断是否为首页
        boolean b = commentInfoPageInfo.isIsLastPage();//判断是否为尾页
        modelAndView.addObject("isIsFirstPage", a);
        modelAndView.addObject("isIsLastPage", b);
        modelAndView.addObject("messages", commentInfoPageInfo);
        return modelAndView;
    }
}
