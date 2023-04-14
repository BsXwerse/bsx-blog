package com.bsxjzb.domain.vo;

import com.bsxjzb.domain.po.Role;
import com.bsxjzb.domain.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAndRoleIdsVO {
    private User user;
    private List<Role> roles;
    private List<Long> roleIds;
}
