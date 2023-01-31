package com.github.wulilinghan.jmb.controller;

import com.github.wulilinghan.jmb.common.exception.ArticleNotFoundException;
import com.github.wulilinghan.jmb.common.global.GlobalData;
import com.github.wulilinghan.jmb.common.pojo.ArticleResult;
import com.github.wulilinghan.jmb.service.IArticle;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.github.wulilinghan.jmb.common.pojo.PageQueryBaseDto;
import com.github.wulilinghan.jmb.service.ITag;

import java.util.ArrayList;

/**
 * @author wuliling Created By 2023-01-18 21:47
 **/
@Controller
@RequestMapping("/")
public class ArticleController {
    public static String themeName = GlobalData.theme;

    @Resource
    ITag iTag;
    @Resource
    private IArticle iArticle;

    @RequestMapping("/")
    public ModelAndView index(PageQueryBaseDto baseDto) {
        ModelAndView modelAndView = new ModelAndView("theme/" + themeName + "/index");
        if (baseDto == null) {
            modelAndView = new ModelAndView("theme/" + themeName + "/error/404");
            return modelAndView;
        }
        modelAndView.addObject("page", iArticle.listTop(baseDto));
        modelAndView.addObject("types", new ArrayList<>());
        modelAndView.addObject("tags", iTag.listTagTop(10));
        modelAndView.addObject("recommendBlogs", iArticle.listTop(7));
        return modelAndView;
    }

    @RequestMapping("article/md/{articleId}")
    public ModelAndView get(@PathVariable("articleId") String articleId) throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("theme/" + themeName + "/blog");
        ArticleResult articleResult = iArticle.get(articleId);
        modelAndView.addObject("blog", articleResult);
        modelAndView.addObject("comments", new ArrayList<>());
        return modelAndView;
    }

    @RequestMapping("article/md/list")
    public ModelAndView list() throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("markdown");
        modelAndView.addObject("value", iArticle.listTop(10));
        return modelAndView;
    }


}