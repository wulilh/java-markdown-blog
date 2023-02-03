package com.github.wulilinghan.jmb.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;

/**
 * 文件id
 *
 * @author wuliling Created By 2023-01-31 14:13
 **/
public class IdUtils {

    /**
     * 根据文件名生成id
     *
     * @param rootFile /
     * @param file     /
     * @return /
     */
    public static String fileUniId(File rootFile, File file) {
        // 文档根路径可以自定义，这里去掉根路径生成id
        String rootAbsolutePath = rootFile.getAbsolutePath().toLowerCase();
        String replace = file.getAbsolutePath().toLowerCase().replace(rootAbsolutePath, "");
        return HashCode.md5ToUniqueNum(DigestUtils.md5Hex(replace));
    }
}
