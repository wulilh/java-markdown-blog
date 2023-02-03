package com.github.wulilinghan.jmb.controller;

import com.github.wulilinghan.jmb.common.pojo.Catalog;
import com.github.wulilinghan.jmb.service.ICatalog;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wuliling Created By 2023-01-18 23:36
 **/
@Controller
public class CatalogController {

    @Resource
    private ICatalog iCatalog;

    @RequestMapping("catalog/{catalogId}")
    public Catalog getCatalog(@PathVariable("catalogId") String catalogId) {
        return iCatalog.getCatalog(catalogId);
    }
}
