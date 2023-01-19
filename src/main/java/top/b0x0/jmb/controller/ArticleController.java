package top.b0x0.jmb.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import top.b0x0.jmb.common.exception.ArticleNotFoundException;
import top.b0x0.jmb.common.global.GlobalData;
import top.b0x0.jmb.common.pojo.ArticleResult;
import top.b0x0.jmb.common.pojo.PageBaseDto;
import top.b0x0.jmb.common.pojo.PageResult;
import top.b0x0.jmb.service.ArticleService;

import java.util.ArrayList;

/**
 * @author wuliling Created By 2023-01-18 21:47
 **/
@Controller
@RequestMapping("/")
public class ArticleController {
    public static String theme = "default";
    //public static String theme = "yummy-jekyll";
//    public static String theme = "amaze";

    @Resource
    ArticleService articleService;

    @RequestMapping("/")
    public ModelAndView index(PageBaseDto baseDto) {
        ModelAndView modelAndView = new ModelAndView("blog/" + theme + "/index");

        if (baseDto == null) {
            modelAndView = new ModelAndView("error/error_404");
            return modelAndView;
        }

        PageResult pageResult = new PageResult(GlobalData.markdownMetaList, baseDto.getCurrPage(), baseDto.getPageSize());
        modelAndView.addObject("blogPageResult", pageResult);
        modelAndView.addObject("newBlogs", new ArrayList<>());
        modelAndView.addObject("hotBlogs", new ArrayList<>());
        modelAndView.addObject("hotTags", new ArrayList<>());
        modelAndView.addObject("pageName", "首页");
        return modelAndView;
    }

    @RequestMapping("article/md/{articleId}")
    public ModelAndView get(@PathVariable("articleId") String articleId) throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("blog/" + theme + "/detail");
        ArticleResult articleResult = articleService.get(articleId);
        modelAndView.addObject("blogDetail", articleResult);
        modelAndView.addObject("pageName", "详情");
        return modelAndView;
    }

    @RequestMapping("article/md/list")
    public ModelAndView list() throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("markdown");
        modelAndView.addObject("value", articleService.list());
        return modelAndView;
    }


    @RequestMapping("article/md/about.html")
    public ModelAndView about() throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("about");
        modelAndView.addObject("content", articleService.getAbout());
        return modelAndView;
    }
}
