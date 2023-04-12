package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.domain.dto.TagListDTO;
import com.bsxjzb.domain.po.Tag;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.domain.vo.TagVO;
import com.bsxjzb.mapper.TagMapper;
import com.bsxjzb.service.TagService;
import com.bsxjzb.util.BeanCopyUtil;
import io.jsonwebtoken.lang.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Override
    public PageVO<Tag> pageTagList(Integer pageNum, Integer pageSize, TagListDTO tagListDTO) {
        LambdaQueryWrapper<Tag> tqw = new LambdaQueryWrapper<>();
        tqw.eq(Strings.hasText(tagListDTO.getName()), Tag::getName, tagListDTO.getName());
        tqw.eq(Strings.hasText(tagListDTO.getRemark()), Tag::getRemark, tagListDTO.getRemark());
        Page<Tag> tagPage = new Page<>(pageNum, pageSize);
        page(tagPage, tqw);
        return new PageVO<>(tagPage.getTotal(), tagPage.getRecords());
    }

    @Override
    public List<TagVO> listAllTag() {
        LambdaQueryWrapper<Tag> tqw = new LambdaQueryWrapper<>();
        tqw.select(Tag::getName, Tag::getId);
        List<Tag> list = list(tqw);
        return BeanCopyUtil.copyBeanList(list, TagVO.class);
    }
}
