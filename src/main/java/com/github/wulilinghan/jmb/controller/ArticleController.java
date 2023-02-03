package com.github.wulilinghan.jmb.controller;

import com.github.wulilinghan.jmb.common.exception.ArticleNotFoundException;
import com.github.wulilinghan.jmb.common.global.GlobalData;
import com.github.wulilinghan.jmb.service.IArticle;
import com.github.wulilinghan.jmb.service.IMessageComment;
import com.github.wulilinghan.jmb.service.ITag;
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
        modelAndView.addObject("comments", iMessageComment.selectByPage(1, -1).getList());
        return modelAndView;
    }

    @RequestMapping("/md/list")
    public ModelAndView list() throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("markdown");
        modelAndView.addObject("value", iArticle.listTop(10));
        return modelAndView;
    }


}
