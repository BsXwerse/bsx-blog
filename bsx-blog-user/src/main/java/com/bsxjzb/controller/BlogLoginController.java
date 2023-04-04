package com.bsxjzb.controller;

import com.bsxjzb.constants.enums.AppHttpCodeEnum;
import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.po.User;
import com.bsxjzb.domain.vo.BlogUserLoginVO;
import com.bsxjzb.exception.SystemException;
import com.bsxjzb.service.BlogLoginService;
import io.lettuce.core.output.BooleanListOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public BlogResponse login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        BlogUserLoginVO blogUserLoginVO = blogLoginService.login(user);
        return BlogResponse.ok(blogUserLoginVO);
    }
}
