package com.yangyang.controller;


import com.yangyang.annotation.AspectAnnotation;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.User;
import com.yangyang.enums.AppHttpCodeEnum;
import com.yangyang.exception.SystemException;
import com.yangyang.service.BlogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "登录", description = "博客登录体系相关接口")
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @ApiOperation(value = "登入接口", notes = "携带参数返回token")
    @PostMapping("/login")
    @AspectAnnotation(businessName = "登录")
    public ResponseResult login(@RequestBody User user) {

        if(!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }

        return blogLoginService.login(user);
    }

    @ApiOperation(value = "登出接口", notes = "登录后登出")
    @PostMapping("/logout")
    @AspectAnnotation(businessName = "登录后登出")
    public ResponseResult logout() {
        return blogLoginService.logout();
    }

}
