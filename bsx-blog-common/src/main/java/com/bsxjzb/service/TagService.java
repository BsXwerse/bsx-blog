package com.bsxjzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bsxjzb.domain.dto.TagListDTO;
import com.bsxjzb.domain.po.Tag;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.domain.vo.TagVO;

import java.util.List;

public interface TagService extends IService<Tag> {
    PageVO<Tag> pageTagList(Integer pageNum, Integer pageSize, TagListDTO tagListDTO);

    List<TagVO> listAllTag();
}
