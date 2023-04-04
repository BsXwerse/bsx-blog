package com.bsxjzb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUserLoginVO {

    private String token;
    private UserInfoVO userInfo;
}