package com.bsxjzb.runner;

import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.domain.po.Article;
import com.bsxjzb.mapper.ArticleMapper;
import com.bsxjzb.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) {
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> map = articles.stream().collect(Collectors.toMap(
                a -> a.getId().toString(),
                a -> a.getViewCount().intValue()
        ));
        redisCache.setCacheMap(SysConstants.REDIS_ARTICLE_VIEWCOUNT_KEY, map);
    }
}
