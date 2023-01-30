package top.b0x0.jmb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.TimeZone;

/**
 * @author wuliling Created By 2023-01-17 22:56
 */
@MapperScan("top.b0x0.jmb.mapper")
@SpringBootApplication
public class JavaMarkdownBlogApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(JavaMarkdownBlogApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
