package top.b0x0.jmb.service;

import top.b0x0.jmb.common.exception.ArticleNotFoundException;
import top.b0x0.jmb.common.pojo.ArticleMetaData;
import top.b0x0.jmb.common.pojo.ArticleResult;

import java.util.List;

/**
 * @author wuliling Created By 2023-01-18 21:45
 **/
public interface IArticle {
    ArticleResult get(String articleId) throws ArticleNotFoundException;

    String getAbout() throws ArticleNotFoundException;

    List<ArticleMetaData> list();

    List<ArticleMetaData> listCatalogArticle(String catalogId);
}
