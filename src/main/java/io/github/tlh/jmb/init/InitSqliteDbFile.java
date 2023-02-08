package io.github.tlh.jmb.init;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * @author wuliling Created By 2023-01-22 14:37
 **/
@Slf4j
@Component
@Order(-99)
public class InitSqliteDbFile implements ApplicationRunner {

    @Value("${spring.datasource.url}")
    private String sqliteUrl;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!StringUtils.hasLength(sqliteUrl) || !(sqliteUrl.split(":").length == 3)) {
            throw new RuntimeException(sqliteUrl + " sqlite url error");
        }
        File dbFile = new File(sqliteUrl.split(":")[2]);
        log.info("Start_InitSqliteDbFile sqliteUrl: [{}] ,dbFile:[{}]", sqliteUrl, dbFile);
        // sqlite文件不存在时创建
        if (!dbFile.exists()) {
            FileUtils.touch(dbFile);
        } else {
            log.info("DbFile is exist sqliteUrl: [{}],dbFile:[{}]", sqliteUrl, dbFile);
        }
        log.info("End_InitSqliteDbFile sqliteUrl: [{}],dbFile:[{}]", sqliteUrl, dbFile);

    }
}
