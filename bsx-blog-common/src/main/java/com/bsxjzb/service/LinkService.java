package com.bsxjzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bsxjzb.domain.po.Link;
import com.bsxjzb.domain.vo.LinkVO;
import com.bsxjzb.domain.vo.PageVO;

import java.util.List;

public interface LinkService extends IService<Link> {
    List<LinkVO> getAllLink();

    PageVO<Link> selectLinkPage(Link link, Integer pageNum, Integer pageSize);
}
