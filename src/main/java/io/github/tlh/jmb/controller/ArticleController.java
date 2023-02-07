package io.github.tlh.jmb.controller;

import io.github.tlh.jmb.common.exception.ArticleNotFoundException;
import io.github.tlh.jmb.common.global.GlobalData;
import io.github.tlh.jmb.service.IArticle;
import io.github.tlh.jmb.service.IMessageComment;
import io.github.tlh.jmb.service.ITag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wuliling Created By 2023-01-18 21:47
 **/
@Controller
@RequestMapping("/article")
public class ArticleController {
    public static String themeName = GlobalData.theme;

    @Resource
    ITag iTag;
    @Resource
    private IArticle iArticle;
    @Resource
    private IMessageComment iMessageComment;

    @RequestMapping("/md/{articleId}")
    public ModelAndView get(@PathVariable("articleId") String articleId) throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("theme/" + themeName + "/blog");
        modelAndView.addObject("blog", iArticle.get(articleId));
        modelAndView.addObject("comments", iMessageComment.listCommentsByArticleId(articleId, 1, -1).getList());
        return modelAndView;
    }

    @RequestMapping("/md/list")
    public ModelAndView list() throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("markdown");
        modelAndView.addObject("value", iArticle.listTop(10));
        return modelAndView;
    }


}
