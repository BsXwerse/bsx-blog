package com.bsxjzb.service.impl;

import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.domain.LoginUser;
import com.bsxjzb.domain.po.User;
import com.bsxjzb.service.AdminLoginService;
import com.bsxjzb.util.JwtUtil;
import com.bsxjzb.util.RedisCache;
import com.bsxjzb.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Map<String, String> login(User user) {
        UsernamePasswordAuthenticationToken upat =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(upat);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJwt(userId);
        redisCache.setCacheObject(SysConstants.REDIS_ADMIN_LOGIN_KEY + userId, loginUser);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

    @Override
    public void logout() {
        redisCache.deleteObject(SysConstants.REDIS_ADMIN_LOGIN_KEY + SecurityUtil.getUserId());
    }

    @PreDestroy
    public void exit() {
        redisCache.fuzzyDelete(SysConstants.REDIS_ADMIN_LOGIN_KEY + "*");
    }
}
