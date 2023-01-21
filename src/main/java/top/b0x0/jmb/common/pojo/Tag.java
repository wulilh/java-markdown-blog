package top.b0x0.jmb.common.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuliling Created By 2023-01-21 15:38
 **/
@Data
public class Tag {
    private Long id;
    private String name;
    private List<ArticleMetaData> blogs = new ArrayList<>();
}
