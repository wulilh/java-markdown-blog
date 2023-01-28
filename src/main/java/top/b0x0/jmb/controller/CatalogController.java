package top.b0x0.jmb.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import top.b0x0.jmb.common.pojo.ArticleMetaData;
import top.b0x0.jmb.service.IArticle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuliling Created By 2023-01-18 23:36
 **/
@Controller
@RequestMapping("/catalog/")
public class CatalogController {

    @Resource
    private IArticle article;

    @RequestMapping("list/{catalogId}")
    public ModelAndView list(@PathVariable("catalogId") String catalogId) {
        ModelAndView modelAndView = new ModelAndView("index");
        List<ArticleMetaData> articleMetaData = article.listCatalogArticle(catalogId);
        Map<String, Object> map = new HashMap<>();
        map.put("data", articleMetaData);
        modelAndView.addObject("articles", map);
        return modelAndView;
    }
}
