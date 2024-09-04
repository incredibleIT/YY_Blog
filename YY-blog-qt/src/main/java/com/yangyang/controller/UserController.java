package com.yangyang.controller;


import com.yangyang.annotation.AspectAnnotation;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.dto.UserDto;
import com.yangyang.domain.entity.User;
import com.yangyang.service.UserService;
import com.yangyang.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户", description = "用户相关功能接口")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户信息", notes = "查询当前登录用户的用户信息接口")
    @GetMapping("/userInfo")
    @AspectAnnotation(businessName = "查询当前登录用户的用户信息接口")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }


    @ApiOperation(value = "用户信息", notes = "更新当前登录用户的用户信息接口")
    @PutMapping("/userInfo")
    @AspectAnnotation(businessName = "更新当前登录用户的用户信息接口")
    public ResponseResult updateUserInfo(@RequestBody UserDto userDto) {
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        return userService.updateUserInfo(user);
    }

    @ApiOperation(value = "注册用户", notes = "用户注册接口")
    @PostMapping("/register")
    @AspectAnnotation(businessName = "用户注册接口")
    public ResponseResult register(@RequestBody UserDto userDto) {
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        return userService.register(user);
    }

}
