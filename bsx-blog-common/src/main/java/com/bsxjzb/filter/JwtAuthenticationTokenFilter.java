package com.bsxjzb.filter;

import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.constants.enums.AppHttpCodeEnum;
import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.LoginUser;
import com.bsxjzb.util.HttpUtil;
import com.bsxjzb.util.JsonUtil;
import com.bsxjzb.util.JwtUtil;
import com.bsxjzb.util.RedisCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (!Strings.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        Claims claims = null;
        try {
            claims = JwtUtil.parseJwt(token);
        } catch (Exception e) {
            e.printStackTrace();
            BlogResponse error = BlogResponse.error(AppHttpCodeEnum.NEED_LOGIN);
            HttpUtil.writeString(response, JsonUtil.objectTOJson(error));
            return;
        }
        String userId = claims.getSubject();
        LoginUser loginUser;
        if (Long.parseLong(userId) == 1L) {
            loginUser = redisCache.getCacheObject(SysConstants.REDIS_ADMIN_LOGIN_KEY + userId);
        } else {
            loginUser = redisCache.getCacheObject(SysConstants.REDIS_USER_LOGIN_KEY + userId);
        }
        if (Objects.isNull(loginUser)) {
            BlogResponse error = BlogResponse.error(AppHttpCodeEnum.NEED_LOGIN);
            HttpUtil.writeString(response, JsonUtil.objectTOJson(error));
            return;
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}
