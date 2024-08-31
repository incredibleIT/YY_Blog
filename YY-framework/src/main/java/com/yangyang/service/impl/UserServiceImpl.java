package com.yangyang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.User;
import com.yangyang.domain.vo.UserInfoVo;
import com.yangyang.mapper.UserMapper;
import com.yangyang.service.UserService;
import com.yangyang.utils.BeanCopyUtils;
import com.yangyang.utils.SecurityUtils;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public ResponseResult userInfo() {
        User user = this.getById(SecurityUtils.getUserId());

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);

    }
}
