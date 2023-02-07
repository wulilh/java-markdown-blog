package io.github.tlh.jmb.controller;

import io.github.tlh.jmb.common.global.GlobalData;
import io.github.tlh.jmb.common.pojo.Tag;
import io.github.tlh.jmb.service.IArticle;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.github.tlh.jmb.common.pojo.PageQueryBaseDto;
import io.github.tlh.jmb.service.ITag;

import java.util.List;

/**
 * @author wuliling Created By 2023-01-30 21:39
 **/
@Controller
public class TagController {
    public static String themeName = GlobalData.theme;

    @Resource
    ITag tagService;
    @Resource
    IArticle iArticle;

    @GetMapping("/tags/{id}")
    public String types(PageQueryBaseDto pageDto, @PathVariable Integer id, Model model) {
        List<Tag> tags = tagService.listTagTop(20);
        if (id == -1) {
            if (!CollectionUtils.isEmpty(tags)) {
                id = tags.get(0).getId();
            }
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", iArticle.listArticleWithTagId(id, pageDto));
        model.addAttribute("activeTagId", id);
        return "theme/" + themeName + "/tags";
    }
}
