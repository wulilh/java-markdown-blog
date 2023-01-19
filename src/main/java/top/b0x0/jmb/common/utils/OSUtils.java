package top.b0x0.jmb.common.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuliling Created By 2023-01-18 20:41
 **/
@Slf4j
public class OSUtils {

    /**
     * 工作目录
     *
     * @return /
     */
    public static String userDir() {
        String property = System.getProperty("user.dir");
        log.info("System.user.dir:{}", property);
        return property;
    }

    /**
     * 系统文件分隔符
     *
     * @return /
     */
    public static String fileSeparator() {
        return System.getProperty("file.separator");
    }

    /**
     * 换行符
     *
     * @return /
     */
    public static String lineSeparator() {
        return System.getProperty("line.separator");
    }

    /**
     * 操作系统名称
     *
     * @return /
     */
    public static String osName() {
        String property = System.getProperty("os.name");
        log.info("System.os.name:{}", property);
        return property;
    }
}
