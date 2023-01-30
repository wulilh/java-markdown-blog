package com.github.wulilinghan.jmb.common.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * @author wuliling Created By 2023-01-22 14:37
 **/
@Slf4j
@Configuration
@Order(-99)
public class InitSqliteDbFile {

    @Value("${spring.datasource.url}")
    private String sqliteUrl;

    @Bean
    public void initSqliteFile() throws Exception {
        if (!StringUtils.hasLength(sqliteUrl) || !(sqliteUrl.split(":").length == 3)) {
            throw new RuntimeException(sqliteUrl + " sqlite url error");
        }
        log.info("sqliteUrl: [{}]", sqliteUrl);
        // sqlite文件不存在时创建
        FileUtils.touch(new File(sqliteUrl.split(":")[2]));
    }

}
