package com.bsxjzb.controller;

import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.po.User;
import com.bsxjzb.domain.vo.UserInfoVO;
import com.bsxjzb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public BlogResponse getUserInfo() {

        UserInfoVO userInfoVO = userService.getUserInfo();
        return BlogResponse.ok(userInfoVO);
    }

    @PutMapping("/userInfo")
    public BlogResponse updateUserInfo(@RequestBody User user) {
        userService.updateById(user);
        return BlogResponse.ok();
    }

    @PostMapping("/register")
    public BlogResponse register(@RequestBody User user) {
        userService.register(user);
        return BlogResponse.ok();
    }
}
