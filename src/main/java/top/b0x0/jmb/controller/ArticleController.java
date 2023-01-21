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
    public static String theme = GlobalData.theme;
    //public static String theme = "yummy-jekyll";
//    public static String theme = "amaze";

    @Resource
    private ArticleService articleService;

    @RequestMapping("/")
    public ModelAndView index(PageBaseDto baseDto) {
        ModelAndView modelAndView = new ModelAndView("theme/" + theme + "/index");
        if (baseDto == null) {
            modelAndView = new ModelAndView("theme/" + theme + "/error/404");
            return modelAndView;
        }
        PageResult pageResult = new PageResult(GlobalData.markdownMetaList, baseDto.getCurrPage(), baseDto.getPageSize());
        modelAndView.addObject("page", pageResult);
        modelAndView.addObject("types", new ArrayList<>());
        modelAndView.addObject("tags", new ArrayList<>());
        modelAndView.addObject("recommendBlogs", new ArrayList<>());
        return modelAndView;
    }

    @RequestMapping("article/md/{articleId}")
    public ModelAndView get(@PathVariable("articleId") String articleId) throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("theme/" + theme + "/blog");
        ArticleResult articleResult = articleService.get(articleId);
        modelAndView.addObject("blog", articleResult);
        return modelAndView;
    }

    @RequestMapping("article/md/list")
    public ModelAndView list() throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("markdown");
        modelAndView.addObject("value", articleService.list());
        return modelAndView;
    }

    @RequestMapping("about")
    public ModelAndView about() throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("theme/" + theme + "/about");
        modelAndView.addObject("content", articleService.getAbout());
        return modelAndView;
    }
}
