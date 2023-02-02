package com.github.wulilinghan.jmb.controller;

import com.github.pagehelper.PageInfo;
import com.github.wulilinghan.jmb.common.enums.MessageTypeEnum;
import com.github.wulilinghan.jmb.common.exception.ArticleNotFoundException;
import com.github.wulilinghan.jmb.common.global.GlobalData;
import com.github.wulilinghan.jmb.common.pojo.ArticleResult;
import com.github.wulilinghan.jmb.common.pojo.MessageCommentInfo;
import com.github.wulilinghan.jmb.common.pojo.PageQueryBaseDto;
import com.github.wulilinghan.jmb.service.IArticle;
import com.github.wulilinghan.jmb.service.IMessageComment;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author wuliling Created By 2023-02-02 21:02
 **/
@Controller
public class MessageCommentController {
    public static String theme = GlobalData.theme;

    @Value("${comment.default.avatar}")
    private String avatar;
    //    @Value("${admin.openid}")
    private String adminOpenid = "";

    @Resource
    private IMessageComment iMessageComment;
    @Resource
    private IArticle iArticle;

    /**
     * 发布留言
     *
     * @param comment /
     * @param session /
     * @return /
     */
    @PostMapping("/POSTComments")
    public String post(MessageCommentInfo comment, HttpSession session) throws ArticleNotFoundException {
        String articleId = comment.getArticleMetaData().getArticleId();
        ArticleResult articleResult = iArticle.get(articleId);
        comment.setArticleMetaData(articleResult.getMeta());
        String loginStatus = (String) session.getAttribute("loginStatus");
        if (loginStatus != null) {
            String openid = (String) session.getAttribute("openid");//QQ标识
            String node_id = (String) session.getAttribute("node_id");//github标识
            comment.setAvatar(avatar);
            comment.setAdminMessage(MessageTypeEnum.visitor.getType());
            if (StringUtils.hasText(node_id)) {
                comment.setAdminMessage(MessageTypeEnum.github.getType());
            }
            // 判断是否为管理员评论
            if (adminOpenid.equals(openid)) {
                comment.setAdminMessage(MessageTypeEnum.admin.getType());
            }
        } else {
            comment.setAdminMessage(MessageTypeEnum.tourist.getType());
            comment.setAvatar(avatar);
        }
        iMessageComment.saveComment(comment);
        return "redirect:/refreshComments";
    }


    @GetMapping("/refreshComments")
    public String refreshComments(PageQueryBaseDto query, Model model) {
        PageInfo<MessageCommentInfo> commentInfoPageInfo = iMessageComment.selectByPage(query.getCurrPage(), query.getPageSize());
        boolean isFirstPage = commentInfoPageInfo.isIsFirstPage(); //判断是否为首页
        boolean isLastPage = commentInfoPageInfo.isIsLastPage();//判断是否为尾页
        model.addAttribute("isIsFirstPage", isFirstPage);
        model.addAttribute("isIsLastPage", isLastPage);
        model.addAttribute("comments", commentInfoPageInfo);
        //将数据返回 blog 页面的th:fragment="commentList"片段，实现局部刷新
        return "theme/" + theme + "/blog :: commentList";
    }
}
