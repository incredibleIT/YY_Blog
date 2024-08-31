package com.yangyang.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.constants.SystemConstants;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.Comment;
import com.yangyang.domain.vo.CommentVo;
import com.yangyang.domain.vo.PageVo;
import com.yangyang.enums.AppHttpCodeEnum;
import com.yangyang.exception.SystemException;
import com.yangyang.mapper.CommentMapper;
import com.yangyang.service.CommentService;
import com.yangyang.service.UserService;
import com.yangyang.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;


    // 所有评论集合
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId);
        commentLambdaQueryWrapper.eq(Comment::getType, commentType);
        commentLambdaQueryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_ROOT_USER_ID);
        Page<Comment> page = new Page<>(pageNum, pageSize);

        this.page(page, commentLambdaQueryWrapper);

        List<Comment> comments = page.getRecords();

        List<CommentVo> commentVos = toCommentVos(comments);


        // 对children进行赋值, 想要一个方法根据根评论Id传回属于此评论的所有子评论
        for(CommentVo commentVo : commentVos) {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }



        return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));

    }

    @Override
    public ResponseResult addComment(Comment comment) {

        if(!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.COMMENT_NOT_EXIST);
        }
        this.save(comment);
        return ResponseResult.okResult();
    }


    private List<CommentVo> toCommentVos(List<Comment> comments) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(comments, CommentVo.class);
        for (CommentVo commentVo : commentVos) {

            //根据createBy 查询并set username
            commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getNickName());


            // 根据toCommentUserId查询并set toCommentUserName
            if(commentVo.getToCommentUserId() != SystemConstants.COMMENT_ROOT_USER_ID) {
                commentVo.setToCommentUserName(userService.getById(commentVo.getToCommentUserId()).getNickName());
            }
        }
        return commentVos;
    }


    private List<CommentVo> getChildren(Long id) {

        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(Comment::getRootId, id);
        commentLambdaQueryWrapper.orderByAsc(Comment::getCreateTime);

        List<Comment> comments = this.list(commentLambdaQueryWrapper);

        List<CommentVo> commentVos = toCommentVos(comments);
        return commentVos;
    }









}
