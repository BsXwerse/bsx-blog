package com.bsxjzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bsxjzb.domain.po.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    List<Article> getHotArticleList();
}
