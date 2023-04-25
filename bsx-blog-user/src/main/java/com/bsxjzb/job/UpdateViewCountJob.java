package com.bsxjzb.job;

import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.domain.po.Article;
import com.bsxjzb.runner.StopScriptRunner;
import com.bsxjzb.service.ArticleService;
import com.bsxjzb.util.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "* 0/10 * * * ? ")
    public void updateViewCount() {
        Map<String, Integer> cacheMap = redisCache.getCacheMap(SysConstants.REDIS_ARTICLE_VIEWCOUNT_KEY);
        List<Article> articles = cacheMap.entrySet().stream().map(
                x -> new Article(Long.parseLong(x.getKey()), x.getValue().longValue())
        ).collect(Collectors.toList());
        articleService.updateBatchById(articles);
    }

    @PreDestroy
    public void exit() {
        updateViewCount();
        StopScriptRunner.deleteStopScript();
        redisCache.deleteObject(SysConstants.REDIS_ARTICLE_VIEWCOUNT_KEY);
    }
}
