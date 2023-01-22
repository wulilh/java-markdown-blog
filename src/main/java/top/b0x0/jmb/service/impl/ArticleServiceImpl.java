package top.b0x0.jmb.service.impl;

import org.springframework.stereotype.Service;
import top.b0x0.jmb.component.MarkDownHandler;
import top.b0x0.jmb.common.exception.ArticleNotFoundException;
import top.b0x0.jmb.common.global.GlobalData;
import top.b0x0.jmb.common.pojo.ArticleMetaData;
import top.b0x0.jmb.common.pojo.ArticleResult;
import top.b0x0.jmb.service.ArticleService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wuliling Created By 2023-01-18 21:45
 **/
@Service
public class ArticleServiceImpl implements ArticleService {

    /**
     * 根据文章 articleId 查找文章
     *
     * @param articleId 文件的articleId
     * @return /
     */
    @Override
    public ArticleResult get(final String articleId) throws ArticleNotFoundException {
        ArticleResult articleResult = new ArticleResult();
        CopyOnWriteArrayList<ArticleMetaData> mdMetaList = GlobalData.articleMetaList;
        for (int i = 0; i < mdMetaList.size(); i++) {
            ArticleMetaData metaData = mdMetaList.get(i);
            if (metaData.getArticleId().equals(articleId)) {
                if (i > 0) {
                    articleResult.setPrev(mdMetaList.get(i - 1));
                }
                if (i < mdMetaList.size() - 1) {
                    articleResult.setNext(mdMetaList.get(i + 1));
                }
                articleResult.setMeta(metaData);
                articleResult.setHtml(MarkDownHandler.mdToHtml(GlobalData.getContent(metaData.getPath().toFile())));
                articleResult.setId(metaData.getArticleId());
                return articleResult;
            }
        }
        throw new ArticleNotFoundException(articleId);
    }


    @Override
    public String getAbout() throws ArticleNotFoundException {
        try {
            return MarkDownHandler.mdToHtml(GlobalData.getContent(GlobalData.aboutFile));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleNotFoundException("about.md");
        }
    }

    @Override
    public List<ArticleMetaData> list() {
        return null;
    }

    @Override
    public List<ArticleMetaData> listCatalogArticle(String catalogId) {
        return GlobalData.catalogIndex.get(catalogId).getArticles();
    }


}
