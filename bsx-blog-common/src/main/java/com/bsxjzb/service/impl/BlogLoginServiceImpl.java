package com.bsxjzb.service.impl;

import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.domain.LoginUser;
import com.bsxjzb.domain.po.User;
import com.bsxjzb.domain.vo.BlogUserLoginVO;
import com.bsxjzb.domain.vo.UserInfoVO;
import com.bsxjzb.service.BlogLoginService;
import com.bsxjzb.util.BeanCopyUtil;
import com.bsxjzb.util.JwtUtil;
import com.bsxjzb.util.RedisCache;
import com.mysql.cj.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public BlogUserLoginVO login(User user) {
        UsernamePasswordAuthenticationToken upat =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(upat);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJwt(userId);
        redisCache.setCacheObject(SysConstants.REDIS_USER_LOGIN_KEY + userId, loginUser);// todo redis过期时间

        UserInfoVO userInfoVO = BeanCopyUtil.copyBean(loginUser.getUser(), UserInfoVO.class);
        return new BlogUserLoginVO(jwt, userInfoVO);
    }

    @Override
    public void logout() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisCache.deleteObject(SysConstants.REDIS_USER_LOGIN_KEY + userId);
    }

    @PreDestroy
    public void exit() {
        redisCache.fuzzyDelete(SysConstants.REDIS_USER_LOGIN_KEY + "*");
    }
}
