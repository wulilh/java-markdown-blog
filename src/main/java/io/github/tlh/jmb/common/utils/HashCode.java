package io.github.tlh.jmb.common.utils;

import com.github.pagehelper.util.StringUtil;

import java.security.MessageDigest;

/**
 * @author wuliling Created By 2023-01-17 11:09
 **/
public class HashCode {

    public static int asInt(String s) {
        if (s == null) {
            return 0;
        }
        char[] name = s.toCharArray();
        int hash = 0;
        if (name.length > 0) {
            for (char c : name) {
                hash = 31 * hash + c;
            }
        }
        return hash;
    }


    public static long asLong(String s) {
        if (s == null) {
            return 0L;
        }
        char[] name = s.toCharArray();
        long hash = 0;
        if (name.length > 0) {
            for (char c : name) {
                hash = 31 * hash + c;
            }
        }
        return Math.abs(hash);
    }

    public static long asLong(String s, long hash) {
        if (s == null) {
            throw new NullPointerException("s");
        }
        char[] name = s.toCharArray();
        if (name.length > 0) {
            for (char c : name) {
                hash = 31 * hash + c;
            }
        }
        return Math.abs(hash);
    }

    /**
     * md5转数值型
     * 这个算法返回值理想长度是16，因为md.length = 16
     */
    public static String md5ToUniqueNum(String md5String) {
        if (md5String == null || "".equals(md5String)) {
            return null;
        }
        // 这里根据MD5时间的值更换16进制abc的大小写
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = md5String.getBytes();
            //获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            mdInst.update(btInput);
            //获得密文
            byte[] md = mdInst.digest();
            char[] str = new char[md.length];
            int k = 0;
            for (byte byte0 : md) {
                //只取高位
                str[k++] = hexDigits[(byte0 >>> 4 & 0xf) % 10];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
