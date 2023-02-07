package io.github.tlh.jmb.controller;

import com.github.pagehelper.PageInfo;
import io.github.tlh.jmb.common.enums.CommentTypeEnum;
import io.github.tlh.jmb.common.enums.MessageTypeEnum;
import io.github.tlh.jmb.common.exception.ArticleNotFoundException;
import io.github.tlh.jmb.common.global.GlobalData;
import io.github.tlh.jmb.common.pojo.ArticleResult;
import io.github.tlh.jmb.common.pojo.MessageBoardInfo;
import io.github.tlh.jmb.common.pojo.MessageCommentInfo;
import io.github.tlh.jmb.common.pojo.PageQueryBaseDto;
import io.github.tlh.jmb.service.IArticle;
import io.github.tlh.jmb.service.IMessageBoard;
import io.github.tlh.jmb.service.IMessageComment;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 留言板
 *
 * @author wuliling Created By 2023-01-22 20:05
 **/
@Controller
public class MessageBoardController {

    @Value("${comment.default.avatar}")
    private String avatar;
    //    @Value("${admin.openid}")
    private String adminOpenid = "";

    @Resource
    private IArticle iArticle;
    @Resource
    private IMessageBoard messageBoard;
    @Resource
    private IMessageComment iMessageComment;

    @GetMapping("messages")
    public ModelAndView messages(PageQueryBaseDto query) {
        ModelAndView modelAndView = new ModelAndView("theme/" + GlobalData.theme + "/message_board");
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
            String nodeId = (String) session.getAttribute("node_id");//github标识
            message.setAvatar(avatar);
            message.setAdminMessage(MessageTypeEnum.visitor.getType());
            if (StringUtils.hasText(nodeId)) {
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
        return "theme/" + GlobalData.theme + "/message_board :: messageList";
    }


    /**
     * 发布文章评论
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
            String nodeId = (String) session.getAttribute("node_id");//github标识
            comment.setAvatar(avatar);
            comment.setAdminComment(CommentTypeEnum.visitor.getType());
            if (StringUtils.hasText(nodeId)) {
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
        return "redirect:/refreshComments/" + articleId;
    }


    @GetMapping("/refreshComments/{articleId}")
    public String refreshComments(@PathVariable("articleId") String articleId, Model model) {
        model.addAttribute("comments", iMessageComment.listCommentsByArticleId(articleId, 1, -1).getList());
        //将数据返回 blog 页面的th:fragment="commentList"片段，实现局部刷新
        return "theme/" + GlobalData.theme + "/blog::commentList";
    }
}
