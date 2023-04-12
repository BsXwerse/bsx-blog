package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.domain.po.ArticleTag;
import com.bsxjzb.mapper.ArticleTagMapper;
import com.bsxjzb.service.ArticleTagService;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
}
