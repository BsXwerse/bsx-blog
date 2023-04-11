package com.bsxjzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bsxjzb.domain.po.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<String> selectPermsByUserId(Long userId);
}
