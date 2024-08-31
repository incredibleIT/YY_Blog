package com.yangyang;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yangyang.mapper")
public class YYBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(YYBlogApplication.class, args);
    }
}
