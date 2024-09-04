package com.yangyang.controller;


import com.yangyang.annotation.AspectAnnotation;
import com.yangyang.domain.ResponseResult;
import com.yangyang.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Api(tags = "分类", description = "文章分类相关功能接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;



    @ApiOperation(value = "分类列表", notes = "获取全部文章分类")
    @GetMapping("getCategoryList")
    @AspectAnnotation(businessName = "获取全部文章分类")
    public ResponseResult getCategoryList(){

        return categoryService.getCategoryList();

    }
}
