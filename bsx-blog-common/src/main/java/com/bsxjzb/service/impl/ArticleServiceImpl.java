package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.domain.dto.AddArticleDTO;
import com.bsxjzb.domain.dto.ArticleDTO;
import com.bsxjzb.domain.po.Article;
import com.bsxjzb.domain.po.ArticleTag;
import com.bsxjzb.domain.po.Category;
import com.bsxjzb.domain.vo.*;
import com.bsxjzb.mapper.ArticleMapper;
import com.bsxjzb.service.ArticleService;
import com.bsxjzb.service.ArticleTagService;
import com.bsxjzb.service.CategoryService;
import com.bsxjzb.util.BeanCopyUtil;
import com.bsxjzb.util.RedisCache;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public List<HotArticleVO> getHotArticleList() {
        LambdaQueryWrapper<Article> aq = new LambdaQueryWrapper<>();
        aq.eq(Article::getStatus, SysConstants.ARTICLE_STATUS_NORMAL);
        aq.orderByDesc(Article::getViewCount);
        Page<Article> articlePage = new Page<>(1, 10);
        page(articlePage,aq);
        List<Article> records = articlePage.getRecords();
        List<HotArticleVO> res = BeanCopyUtil.copyBeanList(records, HotArticleVO.class);
        return res;
    }

    @Override
    public PageVO articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> aqw = new LambdaQueryWrapper<>();
        aqw.eq(Article::getStatus, SysConstants.ARTICLE_STATUS_NORMAL);
        aqw.orderByDesc(Article::getIsTop);
        aqw.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        Page<Article> articlePage = new Page<>(Objects.nonNull(pageNum) ? pageNum : 1, Objects.nonNull(pageSize) ? pageSize : 10);
        page(articlePage, aqw);
        List<Article> records = articlePage.getRecords();
        List<Article> articleList = records.stream().map(o -> o.setCategoryName(categoryService.getById(o.getCategoryId()).getName()))
                .collect(Collectors.toList());
        List<ArticleListVO> articleListVOS = BeanCopyUtil.copyBeanList(articleList, ArticleListVO.class);
        return new PageVO<ArticleListVO>(articlePage.getTotal(), articleListVOS);
    }

    @Override
    public ArticleDetailVO getArticleDetail(Long id) {
        Article a = getById(id);
        ArticleDetailVO articleDetailVO = BeanCopyUtil.copyBean(a, ArticleDetailVO.class);
        Long viewCount = ((Integer) redisCache.getCacheMapValue(SysConstants.REDIS_ARTICLE_VIEWCOUNT_KEY, id.toString())).longValue();
        articleDetailVO.setViewCount(viewCount);
        Category category = categoryService.getById(articleDetailVO.getCategoryId());
        if(category != null) {
            articleDetailVO.setCategoryName(category.getName());
        }
        return articleDetailVO;
    }

    @Override
    public void updateViewCount(Long id) {
        redisCache.incrementCacheMapValue(SysConstants.REDIS_ARTICLE_VIEWCOUNT_KEY, id.toString(), 1);
    }

    @Override
    public void add(AddArticleDTO addArticleDTO) {
        Article article = BeanCopyUtil.copyBean(addArticleDTO, Article.class);
        save(article);
        List<ArticleTag> collect = addArticleDTO.getTags().stream()
                .map(tId -> new ArticleTag(article.getId(), tId)).collect(Collectors.toList());
        articleTagService.saveBatch(collect);
    }

    @Override
    public PageVO<Article> selectArticlePage(Article article, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Article> aqw = new LambdaQueryWrapper<>();
        aqw.like(Strings.hasText(article.getTitle()), Article::getTitle, article.getTitle());
        aqw.like(Strings.hasText(article.getSummary()), Article::getSummary, article.getSummary());
        Page<Article> articlePage = new Page<>(pageNum, pageSize);
        page(articlePage, aqw);
        List<Article> records = articlePage.getRecords();
//        List<HotArticleVO> articleVOS = BeanCopyUtil.copyBeanList(records, HotArticleVO.class);
        return new PageVO<>(articlePage.getTotal(), records);
    }

    @Override
    public void edit(ArticleDTO articleDTO) {
        Article article = BeanCopyUtil.copyBean(articleDTO, Article.class);
        updateById(article);
        LambdaQueryWrapper<ArticleTag> qw = new LambdaQueryWrapper<>();
        qw.eq(ArticleTag::getArticleId, article.getId());
        articleTagService.remove(qw);
        List<ArticleTag> collect = articleDTO.getTags().stream()
                .map(x -> new ArticleTag(article.getId(), x)).collect(Collectors.toList());
        articleTagService.saveBatch(collect);
    }

    @Override
    public ArticleVO getInfo(Long id) {
        ArticleVO articleVO = BeanCopyUtil.copyBean(getById(id), ArticleVO.class);
        LambdaQueryWrapper<ArticleTag> aqw = new LambdaQueryWrapper<>();
        aqw.eq(ArticleTag::getArticleId, id);
        List<ArticleTag> list = articleTagService.list(aqw);
        List<Long> collect = list.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
        articleVO.setTags(collect);
        return articleVO;
    }
}
