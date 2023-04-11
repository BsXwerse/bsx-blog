package com.bsxjzb.util;

import com.bsxjzb.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

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
            id = -1L;
        }
        return id;
    }

    public static boolean isAdmin() {
        Long userId = getUserId();
        return Objects.nonNull(userId) && userId.equals(1L);
    }
}
