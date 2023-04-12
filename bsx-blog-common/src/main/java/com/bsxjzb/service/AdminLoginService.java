package com.bsxjzb.service;

import com.bsxjzb.domain.po.User;

import java.util.Map;

public interface AdminLoginService {
    Map<String, String> login(User user);

    void logout();
}
