package com.github.wulilinghan.jmb.controller;

import com.github.wulilinghan.jmb.common.global.GlobalData;
import com.github.wulilinghan.jmb.common.pojo.PageQueryBaseDto;
import com.github.wulilinghan.jmb.service.IArticle;
import com.github.wulilinghan.jmb.service.ITag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * @author wuliling Created By 2023-02-03 21:41
 **/
@Controller
public class IndexController {
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
}
