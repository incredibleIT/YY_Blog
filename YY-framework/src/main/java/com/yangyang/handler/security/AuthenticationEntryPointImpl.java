package com.yangyang.handler.security;

import com.alibaba.fastjson.JSON;
import com.yangyang.domain.ResponseResult;
import com.yangyang.enums.AppHttpCodeEnum;
import com.yangyang.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();

        //BadCredentialsException  用户名密码错误抛出的错
        //InsufficientAuthenticationException 未登录抛出的错

        ResponseResult result = null;

        if(e instanceof BadCredentialsException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), e.getMessage());
        } else if(e instanceof InsufficientAuthenticationException){
            log.error("出错: {}", e);
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        } else {
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "出错");
        }
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
