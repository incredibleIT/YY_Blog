package com.yangyang;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yangyang.mapper")
public class YYBlogAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(YYBlogAdminApplication.class, args);
    }
}
