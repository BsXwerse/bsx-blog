package com.bsxjzb.controller;

import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.dto.ChangeRoleStatusDTO;
import com.bsxjzb.domain.po.Role;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public BlogResponse list(Role role, Integer pageNum, Integer pageSize) {
        PageVO<Role> pageVO = roleService.selectRolePage(role, pageNum, pageSize);
        return BlogResponse.ok(pageVO);
    }

    @PutMapping("/changeStatus")
    public BlogResponse changeStatus(@RequestBody ChangeRoleStatusDTO changeRoleStatusDTO) {
        Role role = new Role();
        role.setId(changeRoleStatusDTO.getRoleId());
        role.setStatus(changeRoleStatusDTO.getStatus());
        roleService.updateById(role);
        return BlogResponse.ok();
    }

    @PostMapping
    public BlogResponse add(@RequestBody Role role) {
        roleService.insertRole(role);
        return BlogResponse.ok();
    }

    @PutMapping
    public BlogResponse edit(@RequestBody Role role) {
        roleService.updateRole(role);
        return BlogResponse.ok();
    }

    @GetMapping("/{id}")
    public BlogResponse getInfo(@PathVariable("id") Long id) {
        Role role = roleService.getById(id);
        return BlogResponse.ok(role);
    }

    @DeleteMapping("/{id}")
    public BlogResponse remove(@PathVariable("id") Long id) {
        roleService.removeById(id);
        return BlogResponse.ok();
    }
}
