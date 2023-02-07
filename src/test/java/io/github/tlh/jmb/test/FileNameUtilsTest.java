package io.github.tlh.jmb.test;

import io.github.tlh.jmb.TestBase;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

/**
 * @author wuliling Created By 2023-01-18 23:45
 **/
public class FileNameUtilsTest extends TestBase {

    @Test
    public void test() {
        String name = "D:\\website\\articles";
        String name1 = FilenameUtils.getName(name);
        System.out.println("name1 = " + name1);
    }
}
