package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.domain.po.Article;
import com.bsxjzb.domain.po.Category;
import com.bsxjzb.domain.vo.CategoryVO;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.mapper.CategoryMapper;
import com.bsxjzb.service.ArticleService;
import com.bsxjzb.service.CategoryService;
import com.bsxjzb.util.BeanCopyUtil;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    @Override
    public List<CategoryVO> listAllCategory() {
        LambdaQueryWrapper<Category> cqw = new LambdaQueryWrapper<>();
        cqw.eq(Category::getStatus, SysConstants.CATEGORY_STATUS_NORMAL);
        List<Category> list = list(cqw);
        return BeanCopyUtil.copyBeanList(list, CategoryVO.class);
    }

    @Override
    public PageVO<Category> selectCategoryPage(Category category, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Category> cqw = new LambdaQueryWrapper<>();
        cqw.like(Strings.hasText(category.getName()), Category::getName, category.getName());
        cqw.eq(Objects.nonNull(category.getStatus()),Category::getStatus, category.getStatus());
        Page<Category> categoryPage = new Page<>(pageNum, pageSize);
        page(categoryPage, cqw);
        return new PageVO<>(categoryPage.getTotal(), categoryPage.getRecords());
    }
}
