package com.bsxjzb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bsxjzb.domain.po.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
