package com.bsxjzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bsxjzb.domain.po.User;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.domain.vo.UserInfoVO;
import com.bsxjzb.domain.vo.UserVO;

public interface UserService extends IService<User> {
    UserInfoVO getUserInfo();

    void register(User user);

    PageVO<UserVO> selectUserPage(User user, Integer pageNum, Integer pageSize);

    boolean checkUserNameUnique(String username);

    boolean checkPhoneUnique(User user);

    boolean checkEmailUnique(User user);

    void addUser(User user);

    void updateUser(User user);
}
