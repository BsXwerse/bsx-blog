package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bsxjzb.domain.LoginUser;
import com.bsxjzb.domain.po.User;
import com.bsxjzb.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getUserName, username);
        User user = userMapper.selectOne(qw);
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户不存在");
        }
        // todo 管理员还有权限信息
        return new LoginUser(user, null);
    }
}
