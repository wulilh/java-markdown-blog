package io.github.tlh.jmb.service.impl;

import io.github.tlh.jmb.common.pojo.Catalog;
import io.github.tlh.jmb.common.global.GlobalData;
import io.github.tlh.jmb.service.ICatalog;
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

    @Override
    public Catalog getCatalog() {
        return GlobalData.catalog;
    }
}
