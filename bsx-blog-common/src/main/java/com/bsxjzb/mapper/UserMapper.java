package com.bsxjzb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bsxjzb.domain.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
