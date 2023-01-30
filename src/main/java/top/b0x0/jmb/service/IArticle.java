package top.b0x0.jmb.service;

import top.b0x0.jmb.common.exception.ArticleNotFoundException;
import top.b0x0.jmb.common.pojo.ArticleMetaData;
import top.b0x0.jmb.common.pojo.ArticleResult;
import top.b0x0.jmb.common.pojo.PageQueryBaseDto;
import top.b0x0.jmb.common.pojo.PageResult;

import java.util.List;

/**
 * @author wuliling Created By 2023-01-18 21:45
 **/
public interface IArticle {
    ArticleResult get(String articleId) throws ArticleNotFoundException;

    String getAbout() throws ArticleNotFoundException;

    PageResult listTop(PageQueryBaseDto query);

    List<ArticleMetaData> listTop(int size);

    List<ArticleMetaData> listCatalogArticle(String catalogId);

    PageResult listArticleWithTagId(Integer tagId, PageQueryBaseDto query);
}
