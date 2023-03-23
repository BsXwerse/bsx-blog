package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.domain.po.Article;
import com.bsxjzb.mapper.ArticleMapper;
import com.bsxjzb.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public List<Article> getHotArticleList() {
        // todo 学queryWrapper
        return null;
    }
}
