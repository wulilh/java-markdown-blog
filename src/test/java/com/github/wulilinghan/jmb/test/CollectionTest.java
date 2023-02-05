package com.github.wulilinghan.jmb.test;

import com.github.wulilinghan.jmb.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuliling Created By 2023-02-05 11:09
 **/
public class CollectionTest extends TestBase {

    @Test
    public void test() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        int size = 3;
        List<String> strings = CollectionUtils.isEmpty(list) || list.size() <= size
                ? list : list.subList(0, size);
        System.out.println("strings = " + strings);
    }
}
