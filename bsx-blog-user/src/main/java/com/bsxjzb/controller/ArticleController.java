package com.bsxjzb.controller;

import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.po.Article;
import com.bsxjzb.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
        List<Article> hotList = articleService.getHotArticleList();
        return BlogResponse.ok(hotList);
    }
}
