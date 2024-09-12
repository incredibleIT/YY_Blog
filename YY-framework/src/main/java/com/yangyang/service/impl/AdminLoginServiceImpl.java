package com.yangyang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.LoginUser;
import com.yangyang.domain.entity.User;
import com.yangyang.mapper.UserMapper;
import com.yangyang.service.AdminLoginService;
import com.yangyang.utils.JwtUtil;
import com.yangyang.utils.RedisCache;
import com.yangyang.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;


@Service
public class AdminLoginServiceImpl extends ServiceImpl<UserMapper, User> implements AdminLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult adminLogin(User user) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名密码错误");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);


        redisCache.setCacheObject("login:" + userId, loginUser);

        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);

        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        Long userId = SecurityUtils.getUserId();

        redisCache.deleteObject("login:" + userId);

        return ResponseResult.okResult();
    }
}
