package io.github.tlh.jmb.controller;

import io.github.tlh.jmb.common.exception.ArticleNotFoundException;
import io.github.tlh.jmb.common.global.GlobalData;
import io.github.tlh.jmb.common.pojo.Catalog;
import io.github.tlh.jmb.common.pojo.PageQueryBaseDto;
import io.github.tlh.jmb.common.pojo.Tag;
import io.github.tlh.jmb.service.IArticle;
import io.github.tlh.jmb.service.ICatalog;
import io.github.tlh.jmb.service.IMessageComment;
import io.github.tlh.jmb.service.ITag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuliling Created By 2023-02-03 21:41
 **/
@Controller
public class IndexController {

    @Resource
    ITag iTag;
    @Resource
    private IArticle iArticle;
    @Resource
    private ICatalog iCatalog;
    @Resource
    private IMessageComment iMessageComment;


    @RequestMapping("about")
    public ModelAndView about() throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("theme/" + GlobalData.activeTheme + "/about");
        modelAndView.addObject("content", iArticle.getAbout());
        return modelAndView;
    }

    @RequestMapping("/")
    public ModelAndView index(PageQueryBaseDto baseDto) {
        ModelAndView modelAndView = new ModelAndView("theme/" + GlobalData.activeTheme + "/index");
        if (baseDto == null) {
            modelAndView = new ModelAndView("theme/" + GlobalData.activeTheme + "/error/404");
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
        ModelAndView modelAndView = new ModelAndView("theme/" + GlobalData.activeTheme + "/blog");
        modelAndView.addObject("blog", iArticle.get(articleId));
        modelAndView.addObject("comments", iMessageComment.listCommentsByArticleId(articleId, 1, -1).getList());
        return modelAndView;
    }

    @RequestMapping("article/md/list")
    public ModelAndView list() throws ArticleNotFoundException {
        ModelAndView modelAndView = new ModelAndView("markdown");
        modelAndView.addObject("value", iArticle.listTop(10));
        return modelAndView;
    }

    @GetMapping("/tags/{id}")
    public String types(PageQueryBaseDto pageDto, @PathVariable Integer id, Model model) {
        List<Tag> tags = iTag.listTagTop(20);
        if (id == -1) {
            if (!CollectionUtils.isEmpty(tags)) {
                id = tags.get(0).getId();
            }
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", iArticle.listArticleWithTagId(id, pageDto));
        model.addAttribute("activeTagId", id);
        return "theme/" + GlobalData.activeTheme + "/tags";
    }

    @RequestMapping("rootCatalog")
    public Catalog rootCatalog() {
        return iCatalog.getCatalog();
    }

    @RequestMapping("catalog/{catalogId}")
    public Catalog getCatalog(@PathVariable("catalogId") String catalogId) {
        return iCatalog.getCatalog(catalogId);
    }


}
