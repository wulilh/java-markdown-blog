package com.github.wulilinghan.jmb.service;

import com.github.wulilinghan.jmb.common.exception.ArticleNotFoundException;
import com.github.wulilinghan.jmb.common.pojo.*;

import java.util.List;

/**
 * @author wuliling Created By 2023-01-18 21:45
 **/
public interface IArticle {
    ArticleResult get(String articleId) throws ArticleNotFoundException;

    String getAbout() throws ArticleNotFoundException;

    PageResult listTop(PageQueryBaseDto query);

    List<ArticleMetaData> listTop(int size);

    PageResult listArticleWithTagId(Integer tagId, PageQueryBaseDto query);
}
