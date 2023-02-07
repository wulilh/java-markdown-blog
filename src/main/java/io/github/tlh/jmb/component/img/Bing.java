package io.github.tlh.jmb.component.img;

import io.github.tlh.jmb.common.utils.HttpUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuliling Created By 2023-02-07 21:00
 **/
@Component
@SuppressWarnings("all")
public class Bing {
    public static final String URL = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
    public static final String BING_COM = "https://www.bing.com/";

    public String todayImg() {
        Map<String, Object> map = HttpUtil.doGet(URL, HashMap.class);
        System.out.println("map = " + map);
        Map<String, Object> img = ((List<Map<String, Object>>) map.get("images")).get(0);
        String todayImgSuffix = String.valueOf(img.get("url"));
        return BING_COM + todayImgSuffix;
    }
}
