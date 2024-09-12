package com.yangyang.controller;


import com.yangyang.annotation.AspectAnnotation;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.LoginUser;
import com.yangyang.domain.entity.Menu;
import com.yangyang.domain.entity.User;
import com.yangyang.domain.vo.AdminUserInfoVo;
import com.yangyang.domain.vo.RouterVo;
import com.yangyang.domain.vo.UserInfoVo;
import com.yangyang.enums.AppHttpCodeEnum;
import com.yangyang.exception.SystemException;
import com.yangyang.service.AdminLoginService;
import com.yangyang.service.MenuService;
import com.yangyang.service.RoleService;
import com.yangyang.utils.BeanCopyUtils;
import com.yangyang.utils.RedisCache;
import com.yangyang.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RedisCache redisCache;


    @AspectAnnotation(businessName = "后台系统登录接口")
    @PostMapping("user/login")
    public ResponseResult adminLogin(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_MUST_NOTNULL);
        }
        return adminLoginService.adminLogin(user);
    }

    @AspectAnnotation(businessName = "后台系统退出登录接口")
    @PostMapping("user/logout")
    public ResponseResult logout() {
        return adminLoginService.logout();
    }



    @AspectAnnotation(businessName = "获得权限信息接口")
    @GetMapping("/getInfo")
    public ResponseResult getInfo() {

        LoginUser loginUser = SecurityUtils.getLoginUser();

        User user = loginUser.getUser();


        List<String> roleKeyList = roleService.selectRolesByUserId(user.getId());

        List<String> perms = menuService.selectPermsByUserId(user.getId());

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(new AdminUserInfoVo(perms, roleKeyList, userInfoVo));
    }


    @AspectAnnotation(businessName = "获取路由信息接口")
    @GetMapping("/getRouters")
    public ResponseResult getRouters() {
        Long userId = SecurityUtils.getUserId();

        List<Menu> menus = menuService.selectRouterMenuByUserId(userId);

        return ResponseResult.okResult(new RouterVo(menus));
    }
}
