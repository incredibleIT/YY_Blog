package com.yangyang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yangyang"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("YY-Blog", "Hello YY-Blog", "2621515680@qq.com");
        return new ApiInfoBuilder()
                .title("YY-Blog API")
                .description("YY-Blog 博客系统前台后端API接口文档")
                .contact(contact)   // 联系方式
                .version("1.1.0")  // 版本
                .build();
    }
}