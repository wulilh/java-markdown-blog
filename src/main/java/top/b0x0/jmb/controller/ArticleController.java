package top.b0x0.jmb.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import top.b0x0.jmb.common.exception.ArticleNotFoundException;
import top.b0x0.jmb.common.pojo.ArticleResult;
import top.b0x0.jmb.service.ArticleService;

/**
 * @author wuliling Created By 2023-01-18 21:47
 **/
@Controller
@RequestMapping("/article/md/")
public class ArticleController {
    @Resource
    ArticleService articleService;

    @RequestMapping("/get/{articleId}")
    public ModelAndView get(@PathVariable("articleId") String articleId) throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("article");
        ArticleResult articleResult = articleService.get(articleId);
        modelAndView.addObject("article", articleResult);
        return modelAndView;
    }

    @RequestMapping("/list")
    public ModelAndView list() throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("markdown");
        modelAndView.addObject("value", articleService.list());
        return modelAndView;
    }


    @RequestMapping("/about.html")
    public ModelAndView about() throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("about");
        modelAndView.addObject("content", articleService.getAbout());
        return modelAndView;
    }
}
