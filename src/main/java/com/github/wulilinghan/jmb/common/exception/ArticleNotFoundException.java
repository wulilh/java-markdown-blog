package com.github.wulilinghan.jmb.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wuliling Created By 2023-01-17 10:35
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ArticleNotFoundException extends Exception {

    private String articleId;
    private String message;

    public ArticleNotFoundException(String articleId) {
        this.articleId = articleId;
    }
}
