package com.bsxjzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bsxjzb.domain.dto.AddArticleDTO;
import com.bsxjzb.domain.dto.ArticleDTO;
import com.bsxjzb.domain.po.Article;
import com.bsxjzb.domain.vo.ArticleDetailVO;
import com.bsxjzb.domain.vo.ArticleVO;
import com.bsxjzb.domain.vo.HotArticleVO;
import com.bsxjzb.domain.vo.PageVO;

import java.util.List;

public interface ArticleService extends IService<Article> {
    List<HotArticleVO> getHotArticleList();

    PageVO articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ArticleDetailVO getArticleDetail(Long id);

    void updateViewCount(Long id);

    void add(AddArticleDTO addArticleDTO);

    PageVO<Article> selectArticlePage(Article article, Integer pageNum, Integer pageSize);

    void edit(ArticleDTO articleDTO);

    ArticleVO getInfo(Long id);
}
