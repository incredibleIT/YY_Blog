package com.yangyang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.constants.SystemConstants;
import com.yangyang.domain.dto.addArticleDto;
import com.yangyang.domain.entity.Article;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.ArticleTag;
import com.yangyang.domain.entity.Category;
import com.yangyang.domain.entity.Tag;
import com.yangyang.domain.vo.ArticleDetailVo;
import com.yangyang.domain.vo.ArticleListVo;
import com.yangyang.domain.vo.HotArticleVo;
import com.yangyang.domain.vo.PageVo;
import com.yangyang.mapper.ArticleMapper;
import com.yangyang.mapper.ArticleTagMapper;
import com.yangyang.service.ArticleService;
import com.yangyang.service.ArticleTagService;
import com.yangyang.service.CategoryService;
import com.yangyang.service.TagService;
import com.yangyang.utils.BeanCopyUtils;
import com.yangyang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import springfox.documentation.spring.web.readers.operation.ResponseMessagesReader;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;


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

    @Override
    public ResponseResult addArticle(addArticleDto articleDto) {
        //  文章id  + 标签id  主键回显

        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);

        this.save(article);

        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tag -> new ArticleTag(article.getId(), tag))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult getList(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.like(StringUtils.hasText(title), Article::getTitle, title);
        articleLambdaQueryWrapper.like(StringUtils.hasText(summary), Article::getSummary, summary);
        articleLambdaQueryWrapper.eq(Article::getStatus, SystemConstants.NORMAL);

        Page<Article> page = new Page<>(pageNum, pageSize);

        page(page, articleLambdaQueryWrapper);

        List<ArticleDetailVo> articleDetailVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleDetailVo.class);

        return ResponseResult.okResult(new PageVo(articleDetailVos, page.getTotal()));

    }

    @Override
    public ResponseResult articleDetailInfo(Long articleId) {
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();

        articleLambdaQueryWrapper.eq(Article::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        articleLambdaQueryWrapper.eq(Article::getId, articleId);
        Article article = this.getOne(articleLambdaQueryWrapper);


        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId, article.getId());

        List<ArticleTag> articleTagList = articleTagService.list(articleTagLambdaQueryWrapper);

        List<Long> tagIdList = articleTagList.stream()
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        article.setTags(tagIdList);

        return ResponseResult.okResult(article);
    }

    @Override
    public ResponseResult updateArticle(addArticleDto articleDto) {
        //要更改文章信息, 文章标签表信息

        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        //修改文章信息
        this.updateById(article);

        //修改articleTag表

        //TODO 没实现修改articleTag表, 联合主键的更新插入不会

//        List<ArticleTag> articleTags = articleDto.getTags().stream()
//                .map(tagId -> new ArticleTag(article.getId(), tagId))
//                .collect(Collectors.toList());
//
//        articleTagService.saveOrUpdateBatch(articleTags);
        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult removeArticle(Long articleId) {
        //删除文章
        this.removeById(articleId);
        //删除关联
//        articleTagService.removeById(articleId);

        return ResponseResult.okResult();
    }
}
