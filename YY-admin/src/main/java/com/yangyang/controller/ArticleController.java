package com.yangyang.controller;


import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.dto.addArticleDto;
import com.yangyang.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/content/article")
    public ResponseResult addArticle(@RequestBody addArticleDto articleDto) {


        return articleService.addArticle(articleDto);
    }

    @PutMapping("/content/article")
    public ResponseResult updateArticle(@RequestBody addArticleDto articleDto) {

        return articleService.updateArticle(articleDto);

    }

    @GetMapping("/content/article/list")
    @PreAuthorize("@ps.hasPermission('system:user:list')")
    public ResponseResult getList(Integer pageNum, Integer pageSize, String title, String summary) {

        return articleService.getList(pageNum, pageSize, title, summary);

    }


    @GetMapping("/content/article/{id}")
    public ResponseResult articleDetailInfo(@PathVariable("id") Long articleId) {

        return articleService.articleDetailInfo(articleId);


    }

    @DeleteMapping("/content/article/{id}")
    public ResponseResult delArticle(@PathVariable("id") Long articleId) {

        return articleService.removeArticle(articleId);

    }
}
