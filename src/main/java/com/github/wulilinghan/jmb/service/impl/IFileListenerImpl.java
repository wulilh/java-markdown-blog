package com.github.wulilinghan.jmb.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.wulilinghan.jmb.common.pojo.ArticleMetaData;
import com.github.wulilinghan.jmb.common.utils.CommonUtils;
import com.github.wulilinghan.jmb.service.IFileListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

import static com.github.wulilinghan.jmb.common.global.GlobalData.*;

/**
 * @author wuliling Created By 2023-01-18 20:55
 **/
@Service
@Slf4j
public class IFileListenerImpl implements IFileListener {


    /**
     * 文章更新时，更新文章缓存
     */
    @Override
    public void updateArticleCache(String filePath) {

    }

    @Override
    public void deleteArticle(File file) {
        log.info("deleteArticle：" + file);

        // 文档索引删除
        String markdownUniId = CommonUtils.markdownUniId(file);
        ArticleMetaData remove = articleMetaIndex.remove(markdownUniId);
        log.info("articleMetaIndex markdownUniId:[{}] remove = {}", markdownUniId, JSON.toJSONString(remove));

        // 文档集合删除
        boolean removeIf = articleMetaList.removeIf(md -> markdownUniId.equals(md.getArticleId()));
        log.info("articleMetaList markdownUniId:[{}] removeIf = {}", markdownUniId, removeIf);

        // todo 标签下的文章同步删除
    }

    @Override
    public void addArticle(File file) {
        ArticleMetaData articleMetaInfo = getArticleMetaInfo(file);
        log.info("addArticle articleMetaInfo:[{}] ", JSON.toJSONString(articleMetaInfo));
        articleMetaIndex.put(articleMetaInfo.getArticleId(), articleMetaInfo);
        articleMetaList.add(articleMetaInfo);

        // todo 标签下的文章同步新增
    }
}
