package com.bsxjzb.controller;

import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.dto.AddTagDTO;
import com.bsxjzb.domain.dto.EditTagDTO;
import com.bsxjzb.domain.dto.TagListDTO;
import com.bsxjzb.domain.po.Tag;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.domain.vo.TagVO;
import com.bsxjzb.service.TagService;
import com.bsxjzb.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public BlogResponse getTagList(Integer pageNum, Integer pageSize, TagListDTO tagListDTO) {
        PageVO<Tag> pageVO = tagService.pageTagList(pageNum, pageSize, tagListDTO);
        return BlogResponse.ok(pageVO);
    }

    @PostMapping()
    public BlogResponse addTag(@RequestBody AddTagDTO addTagDTO) {
        Tag tag = BeanCopyUtil.copyBean(addTagDTO, Tag.class);
        tagService.save(tag);
        return BlogResponse.ok();
    }

    @DeleteMapping("/{id}")
    public BlogResponse deleteTag(@PathVariable("id") Long id) {
        tagService.removeById(id);
        return BlogResponse.ok();
    }

    @GetMapping("/{id}")
    public BlogResponse getTag(@PathVariable("id") Long id) {
        Tag tag = tagService.getById(id);
        return BlogResponse.ok(tag);
    }

    @PutMapping
    public BlogResponse editTag(@RequestBody EditTagDTO editTagDTO) {
        Tag tag = BeanCopyUtil.copyBean(editTagDTO, Tag.class);
        tagService.updateById(tag);
        return BlogResponse.ok();
    }

    @GetMapping("/listAllTag")
    public BlogResponse listAllTag() {
        List<TagVO> list = tagService.listAllTag();
        return BlogResponse.ok(list);
    }
}
