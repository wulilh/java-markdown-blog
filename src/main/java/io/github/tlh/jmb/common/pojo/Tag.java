package io.github.tlh.jmb.common.pojo;

import io.github.tlh.jmb.common.global.GlobalData;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuliling Created By 2023-01-21 15:38
 **/
@Data
public class Tag {
    private Integer id;
    private String name;
    private List<ArticleMetaData> articleMetaList = new ArrayList<>();

    public Tag() {
    }

    public Tag(String name) {
        this.id = GlobalData.tagIncId.incrementAndGet();
        this.name = name;
    }

    public void addArticle(ArticleMetaData article) {
        if (articleMetaList == null) {
            articleMetaList = new ArrayList<>();
        }
        this.articleMetaList.add(article);
    }
}
