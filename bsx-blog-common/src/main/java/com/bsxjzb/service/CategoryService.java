package com.bsxjzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bsxjzb.domain.po.Category;
import com.bsxjzb.domain.vo.CategoryVO;
import com.bsxjzb.domain.vo.PageVO;

import java.util.List;

public interface CategoryService extends IService<Category> {

    List<CategoryVO> getCategoryList();

    List<CategoryVO> listAllCategory();

    PageVO<Category> selectCategoryPage(Category category, Integer pageNum, Integer pageSize);
}
