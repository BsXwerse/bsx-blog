package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.domain.po.Link;
import com.bsxjzb.domain.vo.LinkVO;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.mapper.LinkMapper;
import com.bsxjzb.service.LinkService;
import com.bsxjzb.util.BeanCopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Override
    public List<LinkVO> getAllLink() {
        LambdaQueryWrapper<Link> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Link::getStatus, SysConstants.FRIEND_LINK_STATUS_NORMAL);
        List<Link> linkList = list(lqw);
        List<LinkVO> linkVOS = BeanCopyUtil.copyBeanList(linkList, LinkVO.class);
        return linkVOS;
    }

    @Override
    public PageVO<Link> selectLinkPage(Link link, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.hasText(link.getName()),Link::getName, link.getName());
        queryWrapper.eq(Objects.nonNull(link.getStatus()),Link::getStatus, link.getStatus());
        Page<Link> linkPage = new Page<>(pageNum, pageSize);
        page(linkPage, queryWrapper);
        return new PageVO<>(linkPage.getTotal(), linkPage.getRecords());
    }
}
