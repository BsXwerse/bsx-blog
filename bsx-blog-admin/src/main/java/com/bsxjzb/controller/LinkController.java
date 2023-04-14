package com.bsxjzb.controller;

import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.po.Link;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public BlogResponse list(Link link, Integer pageNum, Integer pageSize)
    {
        PageVO<Link> pageVo = linkService.selectLinkPage(link,pageNum,pageSize);
        return BlogResponse.ok(pageVo);
    }

    @PostMapping
    public BlogResponse add(@RequestBody Link link){
        linkService.save(link);
        return BlogResponse.ok();
    }

    @PutMapping
    public BlogResponse edit(@RequestBody Link link){
        linkService.updateById(link);
        return BlogResponse.ok();
    }

    @GetMapping(value = "/{id}")
    public BlogResponse getInfo(@PathVariable(value = "id")Long id){
        Link link = linkService.getById(id);
        return BlogResponse.ok(link);
    }

    @DeleteMapping("/{id}")
    public BlogResponse delete(@PathVariable Long id){
        linkService.removeById(id);
        return BlogResponse.ok();
    }

    @PutMapping("/changeLinkStatus")
    public BlogResponse changeLinkStatus(@RequestBody Link link){
        linkService.updateById(link);
        return BlogResponse.ok();
    }
}
