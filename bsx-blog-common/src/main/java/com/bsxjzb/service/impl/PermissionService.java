package com.bsxjzb.service.impl;

import com.bsxjzb.util.SecurityUtil;
import org.springframework.stereotype.Service;

@Service("ps")
public class PermissionService {
    public boolean hasPermission(String permission) {
        if (SecurityUtil.isAdmin()) {
            return true;
        }
        return SecurityUtil.getLoginUser().getPermissions().contains(permission);
    }
}
