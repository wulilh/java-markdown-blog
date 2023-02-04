package com.github.wulilinghan.jmb.service;

import java.io.File;

/**
 * @author wuliling Created By 2023-01-18 20:55
 **/
public interface IFileListener {
    void updateArticleCache(String filePath);

    void deleteArticle(File file);

    void addArticle(File file);
}
