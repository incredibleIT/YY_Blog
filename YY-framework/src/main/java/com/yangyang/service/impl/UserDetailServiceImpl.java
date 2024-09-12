package com.yangyang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yangyang.domain.entity.LoginUser;
import com.yangyang.domain.entity.User;
import com.yangyang.mapper.MenuMapper;
import com.yangyang.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserName, userName);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if(Objects.isNull(user)) {
            throw new RuntimeException("用户不存在");
        }

        //TODO 还要进行权限的封装

        List<String> list = menuMapper.selectPermsByUserId(user.getId());


        return new LoginUser(user, list);

    }
}
