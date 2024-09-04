package com.yangyang.aspect;


import com.alibaba.fastjson.JSON;
import com.yangyang.annotation.AspectAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class CustomerAspect {


    @Pointcut("@annotation(com.yangyang.annotation.AspectAnnotation)")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object aroundAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        Object proceed;

        try {
            methodBeforeHandler(joinPoint);
            proceed = joinPoint.proceed();
            methodAfterHandler(proceed);
        } finally {
            methodEndHandler();
        }
        return proceed;
    }



    private void methodBeforeHandler(ProceedingJoinPoint joinPoint) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}", request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}", getAspectAnnotation(joinPoint).businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), ((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }


    private void methodAfterHandler(Object proceed) {
        log.info("Response       : {}", JSON.toJSONString(proceed));
    }


    private void methodEndHandler() {
        log.info("=======End=======");
    }


    private AspectAnnotation getAspectAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        return methodSignature.getMethod().getAnnotation(AspectAnnotation.class);


    }



}
