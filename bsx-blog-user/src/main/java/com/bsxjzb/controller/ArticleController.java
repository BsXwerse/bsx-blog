package com.bsxjzb.controller;

import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.vo.ArticleDetailVO;
import com.bsxjzb.domain.vo.ArticleListVO;
import com.bsxjzb.domain.vo.ArticleVO;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public BlogResponse hotArticleList() {
        List<ArticleVO> hotList = articleService.getHotArticleList();
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
}
