package top.b0x0.jmb.common.exception;

import lombok.Getter;

/**
 * @author wuliling Created By 2023-01-17 10:35
 **/
public class ArticleNotFoundException extends Exception {

    @Getter
    private String articleId;

    public ArticleNotFoundException(String articleId) {
        this.articleId = articleId;
    }
}
