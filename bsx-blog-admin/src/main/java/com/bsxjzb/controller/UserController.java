package com.bsxjzb.controller;

import com.bsxjzb.constants.enums.AppHttpCodeEnum;
import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.po.Role;
import com.bsxjzb.domain.po.User;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.domain.vo.UserInfoAndRoleIdsVO;
import com.bsxjzb.domain.vo.UserVO;
import com.bsxjzb.exception.SystemException;
import com.bsxjzb.service.RoleService;
import com.bsxjzb.service.UserService;
import com.bsxjzb.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public BlogResponse list(User user, Integer pageNum, Integer pageSize) {
       PageVO<UserVO> pageVO = userService.selectUserPage(user, pageNum, pageSize);
       return BlogResponse.ok(pageVO);
    }

    @PostMapping
    public BlogResponse add(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUsername())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!userService.checkUserNameUnique(user.getUsername())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (!userService.checkPhoneUnique(user)){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (!userService.checkEmailUnique(user)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        userService.addUser(user);
        return BlogResponse.ok();
    }

    @DeleteMapping("/{ids}")
    public BlogResponse delete(@PathVariable("ids") List<Long> ids) {
        if (ids.contains(SecurityUtil.getUserId())) {
            return BlogResponse.error("不能删除当前你正在使用的用户", 500);
        }
        userService.removeBatchByIds(ids);
        return BlogResponse.ok();
    }

    @PutMapping
    public BlogResponse update(@RequestBody User user) {
        userService.updateUser(user);
        return BlogResponse.ok();
    }

    @GetMapping("/{id}")
    public BlogResponse getUserInfoAndRoleIds(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        List<Role> roles = roleService.selectRoleAll();
        List<Long> longs = roleService.selectRoleIdByUserId(id);
        return BlogResponse.ok(new UserInfoAndRoleIdsVO(user, roles, longs));
    }
}
