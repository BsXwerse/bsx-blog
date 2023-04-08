package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.domain.po.Article;
import com.bsxjzb.domain.po.Category;
import com.bsxjzb.domain.vo.ArticleDetailVO;
import com.bsxjzb.domain.vo.ArticleListVO;
import com.bsxjzb.domain.vo.ArticleVO;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.mapper.ArticleMapper;
import com.bsxjzb.service.ArticleService;
import com.bsxjzb.service.CategoryService;
import com.bsxjzb.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<ArticleVO> getHotArticleList() {
        LambdaQueryWrapper<Article> aq = new LambdaQueryWrapper<>();
        aq.eq(Article::getStatus, SysConstants.ARTICLE_STATUS_NORMAL);
        aq.orderByDesc(Article::getViewCount);
        Page<Article> articlePage = new Page<>(1, 10);
        page(articlePage,aq);
        List<Article> records = articlePage.getRecords();
        List<ArticleVO> res = BeanCopyUtil.copyBeanList(records, ArticleVO.class);
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
        //todo redis获取访问量
        Category category = categoryService.getById(articleDetailVO.getCategoryId());
        if(category != null) {
            articleDetailVO.setCategoryName(category.getName());
        }
        return articleDetailVO;
    }
}
