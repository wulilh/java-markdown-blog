package top.b0x0.jmb.common.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wuliling Created By 2023-01-18 21:56
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleResult extends PageResult {

    private ArticleMetaData prev;

    private ArticleMetaData next;

    private ArticleMetaData meta;

    private String html;

}
