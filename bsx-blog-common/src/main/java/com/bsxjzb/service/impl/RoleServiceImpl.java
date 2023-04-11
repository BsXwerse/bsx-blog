package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.domain.po.Role;
import com.bsxjzb.mapper.RoleMapper;
import com.bsxjzb.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
