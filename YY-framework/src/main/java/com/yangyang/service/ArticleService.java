package com.yangyang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangyang.domain.dto.addArticleDto;
import com.yangyang.domain.entity.Article;
import com.yangyang.domain.ResponseResult;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(String id);

    ResponseResult addArticle(addArticleDto articleDto);

    ResponseResult getList(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult articleDetailInfo(Long articleId);

    ResponseResult updateArticle(addArticleDto articleDto);

    ResponseResult removeArticle(Long articleId);

}
