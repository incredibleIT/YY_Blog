package com.yangyang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.constants.SystemConstants;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.Article;
import com.yangyang.domain.entity.Category;
import com.yangyang.domain.entity.Tag;
import com.yangyang.domain.vo.CategoryVo;
import com.yangyang.mapper.CategoryMapper;
import com.yangyang.service.ArticleService;
import com.yangyang.service.CategoryService;
import com.yangyang.utils.BeanCopyUtils;
import kotlin.jvm.internal.Lambda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {

        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        articleQueryWrapper.eq(Article::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleQueryWrapper);
        Set<Long> categoryIds = articleList.stream().map(Article::getCategoryId)
                .collect(Collectors.toSet());


        List<Category> categories = this.listByIds(categoryIds);
        List<Category> categoryList = categories.stream().filter(category -> SystemConstants.LINK_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> tagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tagLambdaQueryWrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = this.list(tagLambdaQueryWrapper);

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}
