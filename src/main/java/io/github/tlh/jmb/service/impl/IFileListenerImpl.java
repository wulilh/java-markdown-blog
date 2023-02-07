package io.github.tlh.jmb.service.impl;

import com.alibaba.fastjson.JSON;
import io.github.tlh.jmb.common.global.GlobalData;
import io.github.tlh.jmb.common.pojo.ArticleMetaData;
import io.github.tlh.jmb.common.utils.CommonUtils;
import io.github.tlh.jmb.service.IFileListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

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
        ArticleMetaData remove = GlobalData.articleMetaIndex.remove(markdownUniId);
        log.info("articleMetaIndex markdownUniId:[{}] remove = {}", markdownUniId, JSON.toJSONString(remove));

        // 文档集合删除
        boolean removeIf = GlobalData.articleMetaList.removeIf(md -> markdownUniId.equals(md.getArticleId()));
        log.info("articleMetaList markdownUniId:[{}] removeIf = {}", markdownUniId, removeIf);

        // todo 标签下的文章同步删除
    }

    @Override
    public void addArticle(File file) {
        ArticleMetaData articleMetaInfo = GlobalData.getArticleMetaInfo(file);
        log.info("addArticle articleMetaInfo:[{}] ", JSON.toJSONString(articleMetaInfo));
        GlobalData.articleMetaIndex.put(articleMetaInfo.getArticleId(), articleMetaInfo);
        GlobalData.articleMetaList.add(articleMetaInfo);

        // todo 标签下的文章同步新增
    }
}
