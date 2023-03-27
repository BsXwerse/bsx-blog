package com.bsxjzb.controller;

import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.po.Article;
import com.bsxjzb.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("test")
    public void doTest(){
        log.info("吼姆吼姆吼姆吼姆吼姆");
    }

    @GetMapping("/Article")
    public BlogResponse getArticle(){
        List<Article> list = articleService.list();
        return BlogResponse.ok(list);
    }

    @GetMapping("/time")
    public void time(Date date){
        System.out.println(date);
    }


}
