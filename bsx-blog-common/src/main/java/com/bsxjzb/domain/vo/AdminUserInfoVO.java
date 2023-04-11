package com.bsxjzb.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AdminUserInfoVO {
    private List<String> permissions;

    private List<String> roles;

    private UserInfoVO user;
}
