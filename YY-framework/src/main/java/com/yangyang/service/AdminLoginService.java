package com.yangyang.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.User;

public interface AdminLoginService extends IService<User> {

    ResponseResult adminLogin(User user);

    ResponseResult logout();

}
