package com.bsxjzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsxjzb.constants.SysConstants;
import com.bsxjzb.constants.enums.AppHttpCodeEnum;
import com.bsxjzb.domain.po.Comment;
import com.bsxjzb.domain.vo.CommentVO;
import com.bsxjzb.domain.vo.PageVO;
import com.bsxjzb.exception.SystemException;
import com.bsxjzb.mapper.CommentMapper;
import com.bsxjzb.service.CommentService;
import com.bsxjzb.service.UserService;
import com.bsxjzb.util.BeanCopyUtil;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;

    @Override
    public PageVO<CommentVO> getCommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> cqw = new LambdaQueryWrapper<>();
        cqw.eq(SysConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId);
        cqw.eq(Comment::getRootId, -1);
        cqw.eq(Comment::getType, commentType);
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, cqw);
        List<CommentVO> commentVOS = BeanCopyUtil.copyBeanList(page.getRecords(), CommentVO.class);
        commentVOS = commentVOS.stream().map(x -> {
            x.setUsername(userService.getById(x.getCreateBy()).getNickName());
            x.setChildren(getChildren(x.getId()));
            if (x.getToCommentUserId() != -1) {
                x.setToCommentUserName(userService.getById(x.getToCommentUserId()).getNickName());
            }
            return x;
        }).collect(Collectors.toList());
        return new PageVO<>(page.getTotal(), commentVOS);
    }

    @Override
    public void addComment(Comment comment) {
        if (!Strings.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
    }

    private List<CommentVO> getChildren(Long id) {
        LambdaQueryWrapper<Comment> cqw = new LambdaQueryWrapper<>();
        cqw.eq(Comment::getRootId, id);
        cqw.orderByAsc(Comment::getCreateTime);
        List<Comment> list = list(cqw);
        List<CommentVO> commentVOS = BeanCopyUtil.copyBeanList(list, CommentVO.class);
        return commentVOS.stream().map(x -> {
            x.setUsername(userService.getById(x.getCreateBy()).getNickName());
            if (x.getToCommentUserId() != -1) {
                x.setToCommentUserName(userService.getById(x.getToCommentUserId()).getNickName());
            }
            return x;
        }).collect(Collectors.toList());
    }
}
