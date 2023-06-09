package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.domain.po.Menu;
import com.bsxjzb.domain.vo.MenuTreeVO;
import com.bsxjzb.mapper.MenuMapper;
import com.bsxjzb.mapper.RoleMapper;
import com.bsxjzb.service.MenuService;
import com.bsxjzb.util.SecurityUtil;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> selectPermsByUserId(Long userId) {
        if (SecurityUtil.isAdmin() || roleMapper.selectRoleIdByUserId(userId).contains(1L)) {
            LambdaQueryWrapper<Menu> mqw = new LambdaQueryWrapper<>();
            mqw.in(Menu::getMenuType, SysConstants.MENU_TYPE_MENU, SysConstants.MENU_TYPE_BUTTON);
            mqw.eq(Menu::getStatus, SysConstants.MENU_STATUS_NORMAL);
            List<Menu> list = list(mqw);
            return list.stream().map(x -> x.getPerms()).collect(Collectors.toList());
        }
        return getBaseMapper().selectPermsByUserId(userId);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        List<Menu> menus;
        if (SecurityUtil.isAdmin() || roleMapper.selectRoleIdByUserId(userId).contains(1L)) {
            menus = getBaseMapper().selectAllRouterMenu();
        } else {
            menus = getBaseMapper().selectRouterMenuTreeByUserId(userId);
        }
        return builderMenuTree(new Menu(0L), menus);
    }

    @Override
    public List<Menu> selectMenuList(Menu menu) {
        LambdaQueryWrapper<Menu> mqw = new LambdaQueryWrapper<>();
        mqw.like(Strings.hasText(menu.getMenuName()), Menu::getMenuName, menu.getMenuName());
        mqw.eq(Strings.hasText(menu.getStatus()), Menu::getStatus, menu.getStatus());
        mqw.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        return list(mqw);
    }

    @Override
    public boolean hasChild(Long id) {
        LambdaQueryWrapper<Menu> mqw = new LambdaQueryWrapper<>();
        mqw.eq(Menu::getParentId, id);
        return count(mqw) > 0;
    }

    private List<Menu> builderMenuTree(Menu menu, List<Menu> menus) {
        return menus.stream().filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(builderMenuTree(m, menus))).collect(Collectors.toList());
    }

    @Override
    public List<MenuTreeVO> buildMenuSelectTree(List<Menu> list) {
        List<MenuTreeVO> collect = list.stream().map(x -> new MenuTreeVO(x.getId(), x.getMenuName(), x.getParentId(), null))
                .collect(Collectors.toList());
        return buildMenuSelectTreeChild(collect, new MenuTreeVO(0L, null, null, null));
    }

    @Override
    public List<Long> selectMenuListByRoleId(Long id) {
        return getBaseMapper().selectMenuListByRoleId(id);
    }

    private List<MenuTreeVO> buildMenuSelectTreeChild(List<MenuTreeVO> collect, MenuTreeVO menuTreeVO) {
        return collect.stream().filter(x -> x.getParentId().equals(menuTreeVO.getId()))
                .map(x -> x.setChildren(buildMenuSelectTreeChild(collect, x)))
                .collect(Collectors.toList());
    }
}
