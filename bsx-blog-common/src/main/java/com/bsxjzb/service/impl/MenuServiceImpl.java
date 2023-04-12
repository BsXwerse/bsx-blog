package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.domain.po.Menu;
import com.bsxjzb.mapper.MenuMapper;
import com.bsxjzb.service.MenuService;
import com.bsxjzb.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Override
    public List<String> selectPermsByUserId(Long userId) {
        if (SecurityUtil.isAdmin()) {
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
        if (SecurityUtil.isAdmin()) {
            menus = getBaseMapper().selectAllRouterMenu();
        } else {
            menus = getBaseMapper().selectRouterMenuTreeByUserId(userId);
        }
        return builderMenuTree(new Menu(0L), menus);
    }

    private List<Menu> builderMenuTree(Menu menu, List<Menu> menus) {
        return menus.stream().filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(builderMenuTree(m, menus))).collect(Collectors.toList());
    }
}
