package com.bsxjzb.handler;

import com.bsxjzb.constants.enums.AppHttpCodeEnum;
import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public BlogResponse systemExceptionHandler(SystemException e) {
        log.error("出现异常：{}", e);
        return BlogResponse.error(e.getMsg(), e.getCode());
    }

    @ExceptionHandler(Exception.class)
    public BlogResponse exceptionhandler(Exception e) {
        log.error("出现异常：{}", e);
        return BlogResponse.error(e.getMessage(), AppHttpCodeEnum.SYSTEM_ERROR.getCode());
    }
}
