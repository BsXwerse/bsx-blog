package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.domain.po.Role;
import com.bsxjzb.mapper.RoleMapper;
import com.bsxjzb.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public List<String> selectRoleKeyByUserId(Long userId) {
        if (userId.equals(1L)) {
            ArrayList<String> list = new ArrayList<>();
            list.add("admin");
            return list;
        }
        return getBaseMapper().selectRoleKeyByUserId(userId);
    }
}
