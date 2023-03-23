package com.bsxjzb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bsxjzb.domain.po.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
