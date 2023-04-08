package com.bsxjzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bsxjzb.domain.po.User;
import com.bsxjzb.domain.vo.UserInfoVO;

public interface UserService extends IService<User> {
    UserInfoVO getUserInfo();

    void register(User user);
}
