package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.constants.enums.AppHttpCodeEnum;
import com.bsxjzb.domain.po.User;
import com.bsxjzb.domain.po.UserRole;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.domain.vo.UserInfoVO;
import com.bsxjzb.domain.vo.UserVO;
import com.bsxjzb.exception.SystemException;
import com.bsxjzb.mapper.UserMapper;
import com.bsxjzb.service.UserRoleService;
import com.bsxjzb.service.UserService;
import com.bsxjzb.util.BeanCopyUtil;
import com.bsxjzb.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserInfoVO getUserInfo() {
        Long userId = SecurityUtil.getUserId();
        User user = getById(userId);
        return BeanCopyUtil.copyBean(user, UserInfoVO.class);
    }

    @Override
    public void register(User user) {
        if(!StringUtils.hasText(user.getUsername())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if(userNameExist(user.getUsername())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        save(user);
    }

    @Override
    public PageVO<UserVO> selectUserPage(User user, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(user.getUsername()),User::getUsername,user.getUsername());
        queryWrapper.eq(StringUtils.hasText(user.getStatus()),User::getStatus,user.getStatus());
        queryWrapper.eq(StringUtils.hasText(user.getPhonenumber()),User::getPhonenumber,user.getPhonenumber());
        Page<User> userPage = new Page<>(pageNum, pageSize);
        page(userPage, queryWrapper);
        List<UserVO> userVOS = BeanCopyUtil.copyBeanList(userPage.getRecords(), UserVO.class);
        return new PageVO<>(userPage.getTotal(), userVOS);
    }

    @Override
    public boolean checkUserNameUnique(String username) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getUsername, username)) == 0;
    }

    @Override
    public boolean checkPhoneUnique(User user) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getPhonenumber, user.getPhonenumber())) == 0;
    }

    @Override
    public boolean checkEmailUnique(User user) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getEmail, user.getEmail())) == 0;
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        if (user.getRoleIds() != null && user.getRoleIds().length > 0) {
            insertUserRole(user);
        }
    }

    @Override
    public void updateUser(User user) {
        updateById(user);
        userRoleService.remove(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, user.getId()));
        insertUserRole(user);
    }

    private void insertUserRole(User user) {
        List<UserRole> collect = Arrays.stream(user.getRoleIds())
                .map(x -> new UserRole(user.getId(), x))
                .collect(Collectors.toList());
        userRoleService.saveBatch(collect);
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getNickName, nickName);
        return count(userLambdaQueryWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername, userName);
        return count(userLambdaQueryWrapper) > 0;
    }
}
