package com.bsxjzb.controller;

import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.vo.ArticleDetailVO;
import com.bsxjzb.domain.vo.ArticleListVO;
import com.bsxjzb.domain.vo.HotArticleVO;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public BlogResponse hotArticleList() {
        List<HotArticleVO> hotList = articleService.getHotArticleList();
        return BlogResponse.ok(hotList);
    }

    @GetMapping("/articleList")
    public BlogResponse ariticleList(Integer pageNum, Integer pageSize, Long categoryId) {
        PageVO<ArticleListVO> pageVO = articleService.articleList(pageNum, pageSize, categoryId);
        return BlogResponse.ok(pageVO);
    }

    @GetMapping("/{id}")
    public BlogResponse getArticleDetail(@PathVariable("id") Long id) {
        ArticleDetailVO articleDetailVO = articleService.getArticleDetail(id);
        return BlogResponse.ok(articleDetailVO);
    }

    @PutMapping("/updateViewCount/{id}")
    public BlogResponse updateViewCount(@PathVariable("id") Long id) {
        articleService.updateViewCount(id);
        return BlogResponse.ok();
    }
}
