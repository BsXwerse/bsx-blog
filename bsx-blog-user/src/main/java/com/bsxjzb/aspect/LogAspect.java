package com.bsxjzb.aspect;

import com.bsxjzb.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {
    @Around("execution(public com.bsxjzb.domain.BlogResponse com.bsxjzb.controller.*.*(..))")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;
        try {
            handleBefore(joinPoint);
            ret = joinPoint.proceed();
            handleAfter(ret);
        } finally {
            log.info("=============END=============" + System.lineSeparator());
        }
        return ret;
    }

    private void handleAfter(Object ret) {
        log.info("Response        :{}", JsonUtil.objectTOJson(ret));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        log.info("============START============");
        log.info("IP              :{}", request.getRemoteHost());
        log.info("URL             :{}", request.getRequestURL());
        log.info("HTTP Method     :{}", request.getMethod());
        log.info("Class Method    :{}.{}", joinPoint.getSignature().getDeclaringTypeName(),
                (joinPoint.getSignature().getName()));
        log.info("Request Args    :{}", JsonUtil.objectTOJson(joinPoint.getArgs()));
    }
}
