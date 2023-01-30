package com.github.wulilinghan.jmb.service.impl;

import com.github.wulilinghan.jmb.service.IFileListener;
import org.springframework.stereotype.Service;

/**
 * @author wuliling Created By 2023-01-18 20:55
 **/
@Service
public class ListenerServiceImpl implements IFileListener {


    /**
     * 文章更新时，更新文章缓存
     */
    @Override
    public void updateArticleCache(String filePath) {

    }
}
