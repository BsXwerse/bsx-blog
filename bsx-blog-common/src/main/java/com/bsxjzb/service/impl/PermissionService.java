package com.bsxjzb.service.impl;

import com.bsxjzb.mapper.RoleMapper;
import com.bsxjzb.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ps")
public class PermissionService {
    @Autowired
    private RoleMapper roleMapper;

    public boolean hasPermission(String permission) {
        if (SecurityUtil.isAdmin() || roleMapper.selectRoleIdByUserId(SecurityUtil.getUserId()).contains(1L)) {
            return true;
        }
        return SecurityUtil.getLoginUser().getPermissions().contains(permission);
    }
}
