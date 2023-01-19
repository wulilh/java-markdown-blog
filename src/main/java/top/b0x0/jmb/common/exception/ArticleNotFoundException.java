package top.b0x0.jmb.common.exception;

import lombok.Getter;

/**
 * @author wuliling Created By 2023-01-17 10:35
 **/
public class ArticleNotFoundException extends Exception {

    @Getter
    private String sha256;

    public ArticleNotFoundException(String sha256) {
        this.sha256 = sha256;
    }
}
