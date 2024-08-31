package com.yangyang.controller;


import com.yangyang.constants.SystemConstants;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.Comment;
import com.yangyang.service.CommentService;
import com.yangyang.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


//不需要token
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {

        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }


    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {

        return commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);

    }



    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment) {
        System.out.println(comment);

        comment.setCreateBy(SecurityUtils.getUserId());


        System.out.println(comment);
        return commentService.addComment(comment);
    }
}
