package top.b0x0.jmb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import top.b0x0.jmb.common.global.GlobalData;
import top.b0x0.jmb.common.pojo.ArticleMetaData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuliling Created By 2023-01-18 21:41
 **/
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<ArticleMetaData> data = GlobalData.markdownMetaList.subList(0, 5);
        Map<String, Object> map = new HashMap<>();
        map.put("getTotalPages",5);
        map.put("data",data);
        modelAndView.addObject("articles", map);
        return modelAndView;
    }

}
