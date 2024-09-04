package com.yangyang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.constants.SystemConstants;
import com.yangyang.domain.entity.Article;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.Category;
import com.yangyang.domain.vo.ArticleDetailVo;
import com.yangyang.domain.vo.ArticleListVo;
import com.yangyang.domain.vo.HotArticleVo;
import com.yangyang.domain.vo.PageVo;
import com.yangyang.mapper.ArticleMapper;
import com.yangyang.service.ArticleService;
import com.yangyang.service.CategoryService;
import com.yangyang.utils.BeanCopyUtils;
import com.yangyang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;


    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);

        Page<Article> articlePage = new Page<>(1, 10);

        page(articlePage, queryWrapper);

        List<Article> articles = articlePage.getRecords();

        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();

        articleQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        articleQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        articleQueryWrapper.orderByDesc(Article::getIsTop);

        Page<Article> articlePage = new Page<>(pageNum, pageSize);

        this.page(articlePage, articleQueryWrapper);

        List<Article> articles = articlePage.getRecords();


        articles = articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);


        return ResponseResult.okResult(new PageVo(articleListVos, articlePage.getTotal()));
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = this.getById(id);

        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_KEY, id.toString());

        article.setViewCount(viewCount.longValue());

        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);


        Category category = categoryService.getById(article.getCategoryId());
        if(Objects.nonNull(category)) {
            articleDetailVo.setCategoryName(category.getName());
        }


        return ResponseResult.okResult(articleDetailVo);

    }

    @Override
    public ResponseResult updateViewCount(String id) {
        redisCache.increment(SystemConstants.REDIS_KEY, id, SystemConstants.REDIS_INCREMENT_STEP);

        return ResponseResult.okResult();
    }
}
