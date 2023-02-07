package io.github.tlh.jmb.service.impl;

import io.github.tlh.jmb.common.exception.ArticleNotFoundException;
import io.github.tlh.jmb.common.global.GlobalData;
import io.github.tlh.jmb.common.pojo.*;
import io.github.tlh.jmb.component.MarkDownHandler;
import io.github.tlh.jmb.service.IArticle;
import com.github.wulilinghan.jmb.common.pojo.*;
import io.github.wulilinghan.jmb.common.pojo.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wuliling Created By 2023-01-18 21:45
 **/
@Service
public class IArticleImpl implements IArticle {

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
        }
        return "";
    }

    @Override
    public PageResult listArticleWithTagId(Integer tagId, PageQueryBaseDto query) {
        Tag tag = GlobalData.tagIndex.get(tagId);
        if (Objects.isNull(tag)) {
            tag = new Tag();
        }
        return new PageResult(tag.getArticleMetaList(), query.getCurrPage(), query.getPageSize());
    }

    @Override
    public PageResult listTop(PageQueryBaseDto query) {
        return new PageResult(GlobalData.articleMetaList, query.getCurrPage(), query.getPageSize());
    }

    @Override
    public List<ArticleMetaData> listTop(int size) {
        return CollectionUtils.isEmpty(GlobalData.articleMetaList) || GlobalData.articleMetaList.size() <= size
                ? GlobalData.articleMetaList : GlobalData.articleMetaList.subList(0, size);
    }


}
