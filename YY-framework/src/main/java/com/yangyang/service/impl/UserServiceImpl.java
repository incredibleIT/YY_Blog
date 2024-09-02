package com.yangyang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.User;
import com.yangyang.domain.vo.UserInfoVo;
import com.yangyang.enums.AppHttpCodeEnum;
import com.yangyang.exception.SystemException;
import com.yangyang.mapper.UserMapper;
import com.yangyang.service.UserService;
import com.yangyang.utils.BeanCopyUtils;
import com.yangyang.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public ResponseResult userInfo() {
        User user = this.getById(SecurityUtils.getUserId());

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);

    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        this.updateById(user);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //用户名, 密码, 昵称, 邮箱notnull
        if(!StringUtils.hasText(user.getUserName())) {

            throw new SystemException(AppHttpCodeEnum.USERNAME_MUST_NOTNULL);

        }

        if(!StringUtils.hasText(user.getPassword())) {

            throw new SystemException(AppHttpCodeEnum.PASSWORD_MUST_NOTNULL);

        }

        if(!StringUtils.hasText(user.getNickName())) {

            throw new SystemException(AppHttpCodeEnum.NICKNAME_MUST_NOTNULL);

        }

        if(!StringUtils.hasText(user.getEmail())) {

            throw new SystemException(AppHttpCodeEnum.EMAIL_MUST_NOTNULL);

        }
        //要求用户名，昵称，邮箱不能和数据库中原有的数据重复


        if(userNameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }

        if(nickNameExist(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }

        if(emailExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.save(user);

        return ResponseResult.okResult();
    }



    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        return this.count(userLambdaQueryWrapper.eq(User::getUserName, userName)) > 0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        return  this.count(userLambdaQueryWrapper.eq(User::getNickName, nickName)) > 0;
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        return this.count(userLambdaQueryWrapper.eq(User::getEmail, email)) > 0;
    }
}
