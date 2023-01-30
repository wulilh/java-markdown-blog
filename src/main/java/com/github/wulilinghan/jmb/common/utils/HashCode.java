package com.github.wulilinghan.jmb.common.utils;

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
}
