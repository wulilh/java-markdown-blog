package io.github.tlh.jmb.service;

import io.github.tlh.jmb.common.exception.ArticleNotFoundException;
import io.github.tlh.jmb.common.pojo.ArticleMetaData;
import io.github.tlh.jmb.common.pojo.ArticleResult;
import io.github.tlh.jmb.common.pojo.PageResult;
import com.github.wulilinghan.jmb.common.pojo.*;
import io.github.tlh.jmb.common.pojo.PageQueryBaseDto;

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
