package com.bsxjzb.controller;

import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.domain.BlogResponse;
import com.bsxjzb.domain.dto.AddCommentDTO;
import com.bsxjzb.domain.po.Comment;
import com.bsxjzb.domain.vo.CommentVO;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.service.CommentService;
import com.bsxjzb.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public BlogResponse getCommentList(Long articleId, Integer pageNum, Integer pageSize) {
        PageVO<CommentVO> pageVO = commentService.getCommentList(SysConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
        return BlogResponse.ok(pageVO);
    }

    @PostMapping
    public BlogResponse addComment(@RequestBody AddCommentDTO addCommentDTO) {
        Comment comment = BeanCopyUtil.copyBean(addCommentDTO, Comment.class);
        commentService.addComment(comment);
        return BlogResponse.ok();
    }

    @GetMapping("/linkCommentList")
    public BlogResponse getLinkCommentList(Integer pageNum, Integer pageSize) {
        PageVO<CommentVO> pageVO = commentService.getCommentList(SysConstants.LINK_COMMENT, null, pageNum, pageSize);
        return BlogResponse.ok(pageVO);
    }
}
