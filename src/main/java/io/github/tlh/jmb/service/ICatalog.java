package io.github.tlh.jmb.service;

import io.github.tlh.jmb.common.pojo.Catalog;

/**
 * @author wuliling Created By 2023-02-03 21:32
 **/
public interface ICatalog {
    Catalog getCatalog(String catalogId);
}
