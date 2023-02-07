package io.github.tlh.jmb.test;

import io.github.tlh.jmb.TestBase;
import io.github.tlh.jmb.component.img.Bing;
import io.github.tlh.jmb.component.img.Unsplash;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

/**
 * @author wuliling Created By 2023-02-07 21:15
 **/
public class ImgTest extends TestBase {

    @Resource
    Bing bing;
    @Resource
    Unsplash unsplash;

    @Test
    public void unsplash_random_1920_1080(){
        String random = unsplash.random_1920_1080();

        System.out.println("random = " + random);
    }

    @Test
    public void unsplash_random(){
        String random = unsplash.random();

        System.out.println("random = " + random);
    }

    @Test
    public void bing_todayImg(){
        String todayImg = bing.todayImg();
        System.out.println("todayImg = " + todayImg);
    }
}
