package com.bsxjzb.controller;

import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.po.Menu;
import com.bsxjzb.domain.vo.MenuTreeVO;
import com.bsxjzb.domain.vo.MenuVO;
import com.bsxjzb.domain.vo.RoleMenuTreeSelectVO;
import com.bsxjzb.service.MenuService;
import com.bsxjzb.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public BlogResponse list(Menu menu) {
        List<Menu> list = menuService.selectMenuList(menu);
        List<MenuVO> menuVOS = BeanCopyUtil.copyBeanList(list, MenuVO.class);
        return BlogResponse.ok(menuVOS);
    }

    @PostMapping
    public BlogResponse add(@RequestBody Menu menu) {
        menuService.save(menu);
        return BlogResponse.ok();
    }

    @PutMapping
    public BlogResponse edit(@RequestBody Menu menu) {
        if (menu.getId().equals(menu.getParentId())) {
            return BlogResponse.error("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能与当前菜单相同", 511);
        }
        menuService.updateById(menu);
        return BlogResponse.ok();
    }

    @GetMapping("/{id}")
    public BlogResponse getInfo(@PathVariable("id") Long id) {
        return BlogResponse.ok(menuService.getById(id));
    }

    @DeleteMapping("/{id}")
    public BlogResponse delete(@PathVariable("id") Long id) {
        if (menuService.hasChild(id)) {
            return BlogResponse.error("存在子菜单不允许删除", 512);
        }
        menuService.removeById(id);
        return BlogResponse.ok();
    }

    @GetMapping("/treeselect")
    public BlogResponse treeselect() {
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<MenuTreeVO> menuTreeVOS = menuService.buildMenuSelectTree(menus);
        return BlogResponse.ok(menuTreeVOS);
    }

    @GetMapping("/roleMenuTreeselect/{roleId}")
    public BlogResponse roleMenuTreeSelect(@PathVariable("roleId") Long id) {
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<MenuTreeVO> menuTreeVOS = menuService.buildMenuSelectTree(menus);
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(id);
        return BlogResponse.ok(new RoleMenuTreeSelectVO(checkedKeys, menuTreeVOS));
    }
}
