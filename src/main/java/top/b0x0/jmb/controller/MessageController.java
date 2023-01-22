package top.b0x0.jmb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.b0x0.jmb.common.pojo.PageQueryBaseDto;
import top.b0x0.jmb.common.pojo.Result;

/**
 * @author wuliling Created By 2023-01-22 20:05
 **/
@Controller
@RequestMapping("/")
public class MessageController {

    @GetMapping("messages")
    public Result message(PageQueryBaseDto query) {

        return Result.ok();
    }
}
