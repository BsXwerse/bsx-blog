package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.domain.po.UserRole;
import com.bsxjzb.mapper.UserRoleMapper;
import com.bsxjzb.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
