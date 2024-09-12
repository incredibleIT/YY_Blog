package com.yangyang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.Category;

public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

}
