package com.bsxjzb.controller;

import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.vo.LinkVO;
import com.bsxjzb.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    public BlogResponse getAllLink() {
        List<LinkVO> linkVOS = linkService.getAllLink();
        return BlogResponse.ok(linkVOS);
    }
}
