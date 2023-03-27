package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.domain.po.Link;
import com.bsxjzb.domain.vo.LinkVO;
import com.bsxjzb.mapper.LinkMapper;
import com.bsxjzb.service.LinkService;
import com.bsxjzb.util.BeanCopyUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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
}
