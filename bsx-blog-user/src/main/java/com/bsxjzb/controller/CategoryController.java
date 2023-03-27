package com.bsxjzb.controller;

import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.vo.CategoryVO;
import com.bsxjzb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public BlogResponse getCategoryList() {
        List<CategoryVO> list = categoryService.getCategoryList();
        return BlogResponse.ok(list);
    }
}
