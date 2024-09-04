package com.yangyang.controller;


import com.yangyang.annotation.AspectAnnotation;
import com.yangyang.domain.ResponseResult;
import com.yangyang.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
@Api(tags = "文章", description = "文章相关功能接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "热门文章列表", notes = "获取浏览量前十名的文章列表")
    @GetMapping("hotArticleList")
    @AspectAnnotation(businessName = "获取浏览量前十名的文章列表")
    public ResponseResult hotArticleList() {
        return articleService.hotArticleList();
    }

    @ApiOperation(value = "所有文章列表", notes = "分页查询所有文章列表")
    @GetMapping("articleList")
    @AspectAnnotation(businessName = "分页查询所有文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "页大小"),
            @ApiImplicitParam(name = "categoryId", value = "分类Id")
    })
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);

    }


    @ApiOperation(value = "文章详细内容", notes = "根据id查询文章具体的内容")
    @GetMapping("/{id}")
    @AspectAnnotation(businessName = "根据id查询具体的内容")
    @ApiImplicitParam(name = "id", value = "文章Id")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {

        return articleService.getArticleDetail(id);
    }

    @ApiOperation(value = "更新浏览量", notes = "根据id进行浏览量的更新")
    @PutMapping("/updateViewCount/{id}")
    @AspectAnnotation(businessName = "根据id进行浏览量的更新")
    @ApiImplicitParam(name = "id", value = "文章Id")
    public ResponseResult updateViewCount(@PathVariable("id") String id) {
        return articleService.updateViewCount(id);
    }
}
