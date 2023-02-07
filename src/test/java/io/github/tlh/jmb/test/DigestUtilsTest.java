package io.github.tlh.jmb.test;

import io.github.tlh.jmb.TestBase;
import io.github.tlh.jmb.common.utils.HashCode;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wuliling Created By 2023-01-17 10:20
 **/
public class DigestUtilsTest extends TestBase {

    @Test
    public void test_md5HexToNum2() throws NoSuchAlgorithmException {
        final String lowerCase = "d:\\website\\md\\Docker.md".toLowerCase();

        final MessageDigest md = MessageDigest.getInstance("MD5");
        final byte[] digest = md.digest(lowerCase.getBytes());
        final BigInteger bigInteger = new BigInteger(1, digest);
        final String uniId = bigInteger.toString(16);
        System.out.println("uniId = " + uniId);
    }

    @Test
    public void test_md5HexToNum() {
        final String lowerCase = "d:\\website\\md\\Docker.md".toLowerCase();
        String md5Hex = DigestUtils.md5Hex(lowerCase);
        System.out.println("md5Hex = " + md5Hex); //0fc52e05dd58aedb41df012b99272299
        final String md5ToNum = HashCode.md5ToUniqueNum(md5Hex);
        System.out.println("md5ToNum = " + md5ToNum);//0650304960627345
    }

    @Test
    public void test_md5Hex() {
        final String lowerCase = "d:\\website\\md\\Docker.md".toLowerCase();
        String sha256Hex = DigestUtils.md5Hex(lowerCase);
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
