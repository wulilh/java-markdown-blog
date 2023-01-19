package top.b0x0.jmb.common.pojo;

import lombok.Data;

/**
 * @author wuliling Created By 2023-01-18 21:56
 **/
@Data
public class ArticleResult {

    private ArticleMetaData prev;

    private ArticleMetaData next;

    private ArticleMetaData meta;

    private String html;
}
