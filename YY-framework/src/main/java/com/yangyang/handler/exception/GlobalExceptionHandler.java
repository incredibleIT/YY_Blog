package com.yangyang.handler.exception;


import com.yangyang.domain.ResponseResult;
import com.yangyang.enums.AppHttpCodeEnum;
import com.yangyang.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e) {
        log.error("异常: {}", e);
        return ResponseResult.errorResult(e.getCode(), e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        log.error("异常: {}", e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), AppHttpCodeEnum.SYSTEM_ERROR.getMsg());
    }

}
