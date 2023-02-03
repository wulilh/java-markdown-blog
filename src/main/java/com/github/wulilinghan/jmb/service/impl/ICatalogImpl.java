package com.github.wulilinghan.jmb.service.impl;

import com.github.wulilinghan.jmb.common.global.GlobalData;
import com.github.wulilinghan.jmb.common.pojo.Catalog;
import com.github.wulilinghan.jmb.service.ICatalog;
import org.springframework.stereotype.Service;

/**
 * @author wuliling Created By 2023-02-03 21:32
 **/
@Service
public class ICatalogImpl implements ICatalog {

    @Override
    public Catalog getCatalog(String catalogId) {
        return GlobalData.catalogIndex.get(catalogId);
    }
}
