package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.domain.po.Article;
import com.bsxjzb.domain.po.Category;
import com.bsxjzb.domain.vo.CategoryVO;
import com.bsxjzb.mapper.CategoryMapper;
import com.bsxjzb.service.ArticleService;
import com.bsxjzb.service.CategoryService;
import com.bsxjzb.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{
    @Autowired
    private ArticleService articleService;

    @Override
    public List<CategoryVO> getCategoryList() {
        LambdaQueryWrapper<Article> aqw = new LambdaQueryWrapper<>();
        aqw.eq(Article::getStatus, SysConstants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(aqw);
        Set<Long> set = list.stream().map(o -> o.getCategoryId()).collect(Collectors.toSet());
        LambdaQueryWrapper<Category> cqw = new LambdaQueryWrapper<>();
        cqw.eq(Category::getStatus, SysConstants.CATEGORY_STATUS_NORMAL);
        cqw.in(Category::getId, set);
        List<Category> categories = list(cqw);
        List<CategoryVO> categoryVOS = BeanCopyUtil.copyBeanList(categories, CategoryVO.class);
        return categoryVOS;
    }
}
