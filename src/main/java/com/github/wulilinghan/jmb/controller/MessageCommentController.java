package com.github.wulilinghan.jmb.controller;

import com.github.wulilinghan.jmb.common.enums.CommentTypeEnum;
import com.github.wulilinghan.jmb.common.exception.ArticleNotFoundException;
import com.github.wulilinghan.jmb.common.global.GlobalData;
import com.github.wulilinghan.jmb.common.pojo.ArticleResult;
import com.github.wulilinghan.jmb.common.pojo.MessageCommentInfo;
import com.github.wulilinghan.jmb.service.IArticle;
import com.github.wulilinghan.jmb.service.IMessageComment;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author wuliling Created By 2023-02-02 21:02
 **/
@Controller
public class MessageCommentController {
    public static String themeName = GlobalData.theme;

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
    public String post(MessageCommentInfo comment, HttpSession session, RedirectAttributes redirectAttributes) throws ArticleNotFoundException {
        String articleId = comment.getArticleMetaData().getArticleId();
        ArticleResult articleResult = iArticle.get(articleId);
        comment.setArticleMetaData(articleResult.getMeta());
        comment.setArticleId(articleId);
        String loginStatus = (String) session.getAttribute("loginStatus");
        if (loginStatus != null) {
            String openid = (String) session.getAttribute("openid");//QQ标识
            String node_id = (String) session.getAttribute("node_id");//github标识
            comment.setAvatar(avatar);
            comment.setAdminComment(CommentTypeEnum.visitor.getType());
            if (StringUtils.hasText(node_id)) {
                comment.setAdminComment(CommentTypeEnum.github.getType());
            }
            // 判断是否为管理员评论
            if (adminOpenid.equals(openid)) {
                comment.setAdminComment(CommentTypeEnum.admin.getType());
            }
        } else {
            comment.setAdminComment(CommentTypeEnum.tourist.getType());
            comment.setAvatar(avatar);
        }
        iMessageComment.saveComment(comment);
//        redirectAttributes.addAttribute(articleId);
        return "redirect:/refreshComments/"+articleId;
    }


    @GetMapping("/refreshComments/{articleId}")
    public String refreshComments(@PathVariable("articleId") String articleId, Model model) {
        model.addAttribute("comments", iMessageComment.listCommentsByArticleId(articleId, 1, -1).getList());
        //将数据返回 blog 页面的th:fragment="commentList"片段，实现局部刷新
        return "theme/" + themeName + "/blog::commentList";
    }
}
