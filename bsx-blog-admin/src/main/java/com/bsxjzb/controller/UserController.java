package com.bsxjzb.controller;

import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.po.User;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.domain.vo.UserVO;
import com.bsxjzb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/list")
    public BlogResponse list(User user, Integer pageNum, Integer pageSize) {
       PageVO<UserVO> pageVO = userService.selectUserPage(user, pageNum, pageSize);
       return BlogResponse.ok(pageVO);
    }
}
