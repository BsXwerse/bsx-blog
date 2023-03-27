package com.bsxjzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bsxjzb.domain.po.Link;
import com.bsxjzb.domain.vo.LinkVO;

import java.util.List;

public interface LinkService extends IService<Link> {
    List<LinkVO> getAllLink();
}
