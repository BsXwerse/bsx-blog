package com.bsxjzb.controller;

import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.dto.AddArticleDTO;
import com.bsxjzb.domain.dto.ArticleDTO;
import com.bsxjzb.domain.po.Article;
import com.bsxjzb.domain.vo.ArticleVO;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public BlogResponse addArticle(@RequestBody AddArticleDTO addArticleDTO) {
        articleService.add(addArticleDTO);
        return BlogResponse.ok();
    }

    @GetMapping("/list")
    public BlogResponse list(Article article, Integer pageNum, Integer pageSize) {
        PageVO<Article> pageVO = articleService.selectArticlePage(article, pageNum, pageSize);
        return BlogResponse.ok(pageVO);
    }

    @PutMapping
    public BlogResponse edit(@RequestBody ArticleDTO articleDTO) {
        articleService.edit(articleDTO);
        return BlogResponse.ok();
    }

    @GetMapping("/{id}")
    public BlogResponse getInfo(@PathVariable("id") Long id) {
        ArticleVO articleVO = articleService.getInfo(id);
        return BlogResponse.ok(articleVO);
    }

    @DeleteMapping("/{id}")
    public BlogResponse delete(@PathVariable("id") Long id) {
        articleService.removeById(id);
        return BlogResponse.ok();
    }
}
