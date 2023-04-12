package com.bsxjzb.controller;

import com.alibaba.excel.EasyExcel;
import com.bsxjzb.constants.enums.AppHttpCodeEnum;
import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.po.Category;
import com.bsxjzb.domain.vo.CategoryVO;
import com.bsxjzb.domain.vo.ExcelCategoryVO;
import com.bsxjzb.service.CategoryService;
import com.bsxjzb.util.BeanCopyUtil;
import com.bsxjzb.util.HttpUtil;
import com.bsxjzb.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public BlogResponse listAllCategory() {
        List<CategoryVO> list = categoryService.listAllCategory();
        return BlogResponse.ok(list);
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            HttpUtil.setExcelDownLoadHeader("分类.xlsx", response);
            List<Category> list = categoryService.list();
            List<ExcelCategoryVO> excelCategoryVOS = BeanCopyUtil.copyBeanList(list, ExcelCategoryVO.class);
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVO.class)
                    .autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVOS);
        } catch (Exception e) {
            BlogResponse blogResponse = BlogResponse.error(AppHttpCodeEnum.SYSTEM_ERROR);
            HttpUtil.writeString(response, JsonUtil.objectTOJson(blogResponse));
        }
    }
}
