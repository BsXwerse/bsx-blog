package com.bsxjzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bsxjzb.domain.dto.AddArticleDTO;
import com.bsxjzb.domain.po.Article;
import com.bsxjzb.domain.vo.ArticleDetailVO;
import com.bsxjzb.domain.vo.ArticleListVO;
import com.bsxjzb.domain.vo.ArticleVO;
import com.bsxjzb.domain.vo.PageVO;

import java.util.List;

public interface ArticleService extends IService<Article> {
    List<ArticleVO> getHotArticleList();

    PageVO articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ArticleDetailVO getArticleDetail(Long id);

    void updateViewCount(Long id);

    void add(AddArticleDTO addArticleDTO);
}
