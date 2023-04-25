package com.bsxjzb.controller;

import com.bsxjzb.BlogAdminApplication;
import com.bsxjzb.constants.enums.AppHttpCodeEnum;
import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.po.Menu;
import com.bsxjzb.domain.po.User;
import com.bsxjzb.domain.vo.AdminUserInfoVO;
import com.bsxjzb.domain.vo.RoutersVO;
import com.bsxjzb.domain.vo.UserInfoVO;
import com.bsxjzb.exception.SystemException;
import com.bsxjzb.runner.StopScriptRunner;
import com.bsxjzb.service.AdminLoginService;
import com.bsxjzb.service.MenuService;
import com.bsxjzb.service.RoleService;
import com.bsxjzb.util.BeanCopyUtil;
import com.bsxjzb.util.SecurityUtil;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;

@RestController
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public BlogResponse login(@RequestBody User user) {
        if (!Strings.hasText(user.getUsername())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        Map<String, String> map = adminLoginService.login(user);
        return BlogResponse.ok(map);
    }

    @GetMapping("/getInfo")
    public BlogResponse getInfo() {
        List<String> perms = menuService.selectPermsByUserId(SecurityUtil.getUserId());
        List<String> roles = roleService.selectRoleKeyByUserId(SecurityUtil.getUserId());
        UserInfoVO userInfoVO = BeanCopyUtil.copyBean(SecurityUtil.getLoginUser().getUser(), UserInfoVO.class);
        return BlogResponse.ok(new AdminUserInfoVO(perms, roles, userInfoVO));
    }

    @GetMapping("/getRouters")
    public BlogResponse getRouters() {
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(SecurityUtil.getUserId());
        return BlogResponse.ok(new RoutersVO(menus));
    }

    @PostMapping("/user/logout")
    public BlogResponse logout() {
        adminLoginService.logout();
        return BlogResponse.ok();
    }

    @PreDestroy
    public void deleteStopScript() {
        StopScriptRunner.deleteStopScript();
    }
}
