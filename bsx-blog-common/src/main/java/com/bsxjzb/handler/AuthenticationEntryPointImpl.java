package com.bsxjzb.handler;

import com.bsxjzb.constants.enums.AppHttpCodeEnum;
import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.util.HttpUtil;
import com.bsxjzb.util.JsonUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        authException.printStackTrace();
        BlogResponse result = null;
        if(authException instanceof BadCredentialsException){
            result = BlogResponse.error(authException.getMessage(), AppHttpCodeEnum.LOGIN_ERROR.getCode());
        }else if(authException instanceof InsufficientAuthenticationException){
            result = BlogResponse.error(AppHttpCodeEnum.NEED_LOGIN);
        }else{
            result = BlogResponse.error("认证或授权失败", AppHttpCodeEnum.SYSTEM_ERROR.getCode());
        }
        //直接通过response的Writer将ResponseResult写入响应体
        HttpUtil.writeString(response, JsonUtil.objectTOJson(result));
    }
}
