package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.domain.po.Role;
import com.bsxjzb.domain.po.RoleMenu;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.mapper.RoleMapper;
import com.bsxjzb.service.RoleMenuService;
import com.bsxjzb.service.RoleService;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long userId) {
        if (userId.equals(1L)) {
            ArrayList<String> list = new ArrayList<>();
            list.add("admin");
            return list;
        }
        return getBaseMapper().selectRoleKeyByUserId(userId);
    }

    @Override
    public PageVO<Role> selectRolePage(Role role, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Role> rqw = new LambdaQueryWrapper<>();
        rqw.like(Strings.hasText(role.getRoleName()), Role::getRoleName, role.getRoleName());
        rqw.eq(Strings.hasText(role.getStatus()), Role::getStatus, role.getStatus());
        rqw.orderByAsc(Role::getRoleSort);
        Page<Role> rolePage = new Page<>(pageNum, pageSize);
        page(rolePage, rqw);
        return new PageVO<>(rolePage.getTotal(), rolePage.getRecords());
    }

    @Override
    public void insertRole(Role role) {
        save(role);
        if (role.getMenuIds() != null && role.getMenuIds().length > 0) {
            insertRoleMenu(role);
        }
    }

    @Override
    public void updateRole(Role role) {
        updateById(role);
        roleMenuService.deleteRoleMenuByRoleId(role.getId());
        insertRoleMenu(role);
    }

    @Override
    public List<Role> selectRoleAll() {
        return list(Wrappers.<Role>lambdaQuery().eq(Role::getStatus, SysConstants.ROLE_STATUS_NORMAL));
    }

    @Override
    public List<Long> selectRoleIdByUserId(Long id) {
        return getBaseMapper().selectRoleIdByUserId(id);
    }

    private void insertRoleMenu(Role role) {
        List<RoleMenu> collect = Arrays.stream(role.getMenuIds())
                .map(x -> new RoleMenu(role.getId(), x)).collect(Collectors.toList());
        roleMenuService.saveBatch(collect);
    }
}
