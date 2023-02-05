package com.github.wulilinghan.jmb.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.github.wulilinghan.jmb.common.exception.ArticleNotFoundException;
import com.github.wulilinghan.jmb.common.global.GlobalData;
import com.github.wulilinghan.jmb.service.IArticle;

/**
 * @author wuliling Created By 2023-01-28 15:40
 **/
@Controller
public class AboutController {
    public static String themeName = GlobalData.theme;

    @Resource
    private IArticle article;

    @RequestMapping("about")
    public ModelAndView about() throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("theme/" + themeName + "/about");
        modelAndView.addObject("content", article.getAbout());
        return modelAndView;
    }
}
