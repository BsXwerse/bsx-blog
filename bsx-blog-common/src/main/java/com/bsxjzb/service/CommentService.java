package com.bsxjzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bsxjzb.domain.po.Comment;
import com.bsxjzb.domain.vo.CommentVO;
import com.bsxjzb.domain.vo.PageVO;

public interface CommentService extends IService<Comment> {
    PageVO<CommentVO> getCommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    void addComment(Comment comment);
}
