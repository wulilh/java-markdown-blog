package top.b0x0.jmb.common.pojo;

import lombok.Data;

/**
 * @author wuliling Created By 2023-01-18 21:53
 **/
@Data
public class Page {
    private String logo = "<img class=\"align-bottom\" src=\"/bug32.png\"/><img src=\"/cat48.png\">不能约的Secret";
    private String footer = " &copy; Aladi 2018 <a href=\"mailto:234369425@qq.com\"><svg viewBox=\"0 0 14 16\" version=\"1.1\" width=\"16\" height=\"18\" style=\"fill:yellow\" aria-hidden=\"true\"><path fill-rule=\"evenodd\" d=\"M0 4v8c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V4c0-.55-.45-1-1-1H1c-.55 0-1 .45-1 1zm13 0L7 9 1 4h12zM1 5.5l4 3-4 3v-6zM2 12l3.5-3L7 10.5 8.5 9l3.5 3H2zm11-.5l-4-3 4-3v6z\"></path></svg></a>\n" +
            "    <br/>\n" +
            "     <a href=\"https://github.com/234369425/Markdown-Blog\" target=\"_blank\">\n" +
            "     <img src=\"/cat32.png\"><span class=\"badge badge-primary\">Markdown blog，welcome join</a></span>";
    private String author = "wuliling";
    private String keywords = "";
    private String titleSuffix = "个人随笔";
    private Integer pageSize = 10;
    private Integer newestSize = 5;
}