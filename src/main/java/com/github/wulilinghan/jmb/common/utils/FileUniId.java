package com.github.wulilinghan.jmb.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;

/**
 * 文件id
 *
 * @author wuliling Created By 2023-01-31 14:13
 **/
public class FileUniId {

    public static String numId(File file) {
        return HashCode.md5ToUniqueNum(DigestUtils.md5Hex(file.getAbsolutePath().toLowerCase()));
    }
}
