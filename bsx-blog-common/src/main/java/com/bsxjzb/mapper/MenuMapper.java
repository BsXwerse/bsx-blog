package com.bsxjzb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bsxjzb.domain.po.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}
