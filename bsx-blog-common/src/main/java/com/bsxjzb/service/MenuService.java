package com.bsxjzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bsxjzb.domain.po.Menu;
import com.bsxjzb.domain.vo.MenuTreeVO;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<Menu> selectMenuList(Menu menu);

    boolean hasChild(Long id);

    List<MenuTreeVO> buildMenuSelectTree(List<Menu> list);

    List<Long> selectMenuListByRoleId(Long id);
}
