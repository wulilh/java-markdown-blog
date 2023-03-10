package io.github.tlh.jmb.component.img;

import io.github.tlh.jmb.common.config.WebSiteConfig;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wuliling Created By 2023-01-22 10:55
 **/
@Component
@SuppressWarnings("all")
public class Unsplash {
    private static final String token = "mRUWESw9K4cIX7A4g9J0m9sOrikMw3OideLlRHfL3t8";
    private static final String sourceUrl = "https://source.unsplash.com";

    @Resource
    private WebSiteConfig webSiteConfig;

    /**
     * 短时间内重复访问会返回相同的图片
     *
     * @return /
     */
    public String random() {
        return sourceUrl + "/random";
    }

    public String random_1920_1080() {
        return sourceUrl + "/random/" + 1920 + "x" + 1080;
    }

    /**
     * 如果原图片不是这种长宽比，unsplash 会对图片进行裁剪，某些部分就会丢失。
     * 如果你想保持图片的原始比例，可以把高度设成 0
     *
     * @param width  1280
     * @param height 720
     * @return /
     */
    public String random(String width, String height) {
        return sourceUrl + "/random/" + width + "x" + height;
    }

    /**
     * @param width    1280
     * @param height   720
     * @param keywords nature,water
     * @return /
     */
    public String random(String width, String height, List<String> keywords) {
        String s = sourceUrl + "/random/" + width + "x" + height;
        if (!CollectionUtils.isEmpty(keywords)) {
            s = s + "/?" + String.join(",", keywords);
        }
        return s;
    }
}
