package com.bsxjzb.util;

import com.bsxjzb.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    public static Long getUserId() {
        Long id;
        try {
            id = getLoginUser().getUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
            id = -1L;
        }
        return id;
    }
}
