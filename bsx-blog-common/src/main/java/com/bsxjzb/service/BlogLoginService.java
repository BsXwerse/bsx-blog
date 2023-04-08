package com.bsxjzb.service;

import com.bsxjzb.domain.po.User;
import com.bsxjzb.domain.vo.BlogUserLoginVO;

public interface BlogLoginService {
    BlogUserLoginVO login(User user);

    void logout();
}
