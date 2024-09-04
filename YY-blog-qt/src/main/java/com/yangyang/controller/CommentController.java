package com.yangyang.controller;


import com.yangyang.annotation.AspectAnnotation;
import com.yangyang.constants.SystemConstants;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.dto.AddCommentDto;
import com.yangyang.domain.entity.Comment;
import com.yangyang.service.CommentService;
import com.yangyang.utils.BeanCopyUtils;
import com.yangyang.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


//不需要token
@RestController
@RequestMapping("/comment")
@Api(tags = "评论", description = "文章及友链评论相关功能接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "文章评论列表", notes = "分页获取全部对文章的评论")
    @GetMapping("/commentList")
    @AspectAnnotation(businessName = "分页获取全部对文章的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章Id"),
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "页大小")
    })
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {

        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @ApiOperation(value = "友链评论列表", notes = "分页获取全部对友链的评论")
    @GetMapping("/linkCommentList")
    @AspectAnnotation(businessName = "分页获取全部对友链的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "页大小")
    })
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {

        return commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);

    }


    @ApiOperation(value = "发表评论", notes = "发表评论接口")
    @PostMapping
    @AspectAnnotation(businessName = "发表评论接口")
    public ResponseResult addComment(@RequestBody AddCommentDto commentDto) {

        commentDto.setCreateBy(SecurityUtils.getUserId());

        Comment comment = BeanCopyUtils.copyBean(commentDto, Comment.class);

        return commentService.addComment(comment);
    }
}
