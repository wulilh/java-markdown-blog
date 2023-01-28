package top.b0x0.jmb.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import top.b0x0.jmb.common.exception.ArticleNotFoundException;
import top.b0x0.jmb.common.global.GlobalData;
import top.b0x0.jmb.service.IArticle;

/**
 * @author tanglinghan Created By 2023-01-28 15:40
 **/
@Controller
@RequestMapping("/")
public class AboutController {
    public static String theme = GlobalData.theme;

    @Resource
    private IArticle article;

    @RequestMapping("about")
    public ModelAndView about() throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("theme/" + theme + "/about");
        modelAndView.addObject("content", article.getAbout());
        return modelAndView;
    }
}
