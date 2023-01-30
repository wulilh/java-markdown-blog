package com.github.wulilinghan.jmb.test;

import com.github.wulilinghan.jmb.TestBase;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author wuliling Created By 2023-01-17 10:20
 **/
public class DigestUtilsTest extends TestBase {

    @Test
    public void test_md5Hex() {
        String sha256Hex = DigestUtils.md5Hex("ABCDEFG");
        System.out.println("sha256Hex = " + sha256Hex);
    }

    @Test
    public void test_sha256Hex() {
        String sha256Hex = DigestUtils.sha256Hex("ABCDEFG");
        System.out.println("sha256Hex = " + sha256Hex);
    }

    @Test
    public void test_sha256HexFile() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("D:\\website\\articles\\生活\\边城.md"));
        String sha256Hex = DigestUtils.sha256Hex(bytes);
        System.out.println("sha256Hex = " + sha256Hex);
    }
}
