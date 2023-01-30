package com.github.wulilinghan.jmb.controller;

import com.github.pagehelper.PageInfo;
import com.github.wulilinghan.jmb.common.enums.MessageTypeEnum;
import com.github.wulilinghan.jmb.common.global.GlobalData;
import com.github.wulilinghan.jmb.common.pojo.MessageBoardInfo;
import com.github.wulilinghan.jmb.common.pojo.PageQueryBaseDto;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.github.wulilinghan.jmb.service.IMessageBoard;

/**
 * 留言板
 *
 * @author wuliling Created By 2023-01-22 20:05
 **/
@Controller
public class MessageBoardController {
    public static String theme = GlobalData.theme;

    @Value("${comment.default.avatar}")
    private String avatar;
    //    @Value("${admin.openid}")
    private String adminOpenid = "";

    @Resource
    private IMessageBoard messageBoard;

    @GetMapping("messages")
    public ModelAndView messages(PageQueryBaseDto query) {
        ModelAndView modelAndView = new ModelAndView("theme/" + theme + "/message_board");
        PageInfo<MessageBoardInfo> commentInfoPageInfo = messageBoard.selectByPage(query.getCurrPage(), query.getPageSize());
        boolean isFirstPage = commentInfoPageInfo.isIsFirstPage(); //判断是否为首页
        boolean isLastPage = commentInfoPageInfo.isIsLastPage();//判断是否为尾页
        modelAndView.addObject("isIsFirstPage", isFirstPage);
        modelAndView.addObject("isIsLastPage", isLastPage);
        modelAndView.addObject("messages", commentInfoPageInfo);
        return modelAndView;
    }

    /**
     * 发布留言
     *
     * @param message /
     * @param session /
     * @return /
     */
    @PostMapping("/POSTMessages")
    public String post(MessageBoardInfo message, HttpSession session) {
        String loginStatus = (String) session.getAttribute("loginStatus");
        if (loginStatus != null) {
            String openid = (String) session.getAttribute("openid");//QQ标识
            String node_id = (String) session.getAttribute("node_id");//github标识
            message.setAvatar(avatar);
            message.setAdminMessage(MessageTypeEnum.visitor.getType());
            if (StringUtils.hasText(node_id)) {
                message.setAdminMessage(MessageTypeEnum.github.getType());
            }
            // 判断是否为管理员评论
            if (adminOpenid.equals(openid)) {
                message.setAdminMessage(MessageTypeEnum.admin.getType());
            }
        } else {
            message.setAdminMessage(MessageTypeEnum.tourist.getType());
            message.setAvatar(avatar);
        }
        messageBoard.saveMessage(message);
        return "redirect:/refreshMessages";
    }

    @GetMapping("/refreshMessages")
    public String message(PageQueryBaseDto query, Model model) {
        PageInfo<MessageBoardInfo> commentInfoPageInfo = messageBoard.selectByPage(query.getCurrPage(), query.getPageSize());
        boolean isFirstPage = commentInfoPageInfo.isIsFirstPage(); //判断是否为首页
        boolean isLastPage = commentInfoPageInfo.isIsLastPage();//判断是否为尾页
        model.addAttribute("isIsFirstPage", isFirstPage);
        model.addAttribute("isIsLastPage", isLastPage);
        model.addAttribute("messages", commentInfoPageInfo);
        //将数据返回message_board页面的th:fragment="messageList"片段，实现局部刷新
        return "theme/" + theme + "/message_board :: messageList";
    }
}
