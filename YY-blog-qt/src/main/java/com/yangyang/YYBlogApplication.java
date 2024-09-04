package com.yangyang;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.yangyang.mapper")
@EnableScheduling
@EnableSwagger2
public class YYBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(YYBlogApplication.class, args);
    }
}
