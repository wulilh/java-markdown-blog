package com.github.wulilinghan.jmb.common.utils;

import com.github.wulilinghan.jmb.common.global.GlobalData;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;

/**
 * 杂七杂八 工具类
 *
 * @author wuliling Created By 2023-01-31 14:13
 **/
public class CommonUtils {

    /**
     * 根据文件名生成id
     *
     * @param rootFile /
     * @param file     /
     * @return /
     */
    public static String markdownUniId(File rootFile, File file) {
        // 文档根路径可以自定义，这里去掉根路径生成id
        String rootAbsolutePath = rootFile.getAbsolutePath().toLowerCase();
        String replace = file.getAbsolutePath().toLowerCase().replace(rootAbsolutePath, "");
        return HashCode.md5ToUniqueNum(DigestUtils.md5Hex(replace));
    }

    public static String markdownUniId(File file) {
        return markdownUniId(GlobalData.MARKDOWN_DIR_FILE, file);
    }

    /**
     * 获取Markdown文档相对路径
     * 配置的markdown-dir 根；路径为 D:\weibsite\md
     * 入参数  D:\weibsite\md\技术\Spring.md
     * 返回的的是 \技术\Spring.md
     *
     * @param file /
     */
    public static String getMarkdownRelativePath(File file) {
        String rootAbsolutePath = GlobalData.MARKDOWN_DIR_FILE.getAbsolutePath().toLowerCase();
        return file.getAbsolutePath().toLowerCase().replace(rootAbsolutePath, "");
    }
}
