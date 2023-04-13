package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.domain.po.RoleMenu;
import com.bsxjzb.mapper.RoleMenuMapper;
import com.bsxjzb.service.RoleMenuService;
import org.springframework.stereotype.Service;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public void deleteRoleMenuByRoleId(Long id) {
        LambdaQueryWrapper<RoleMenu> rqw = new LambdaQueryWrapper<>();
        rqw.eq(RoleMenu::getRoleId, id);
        remove(rqw);
    }
}
