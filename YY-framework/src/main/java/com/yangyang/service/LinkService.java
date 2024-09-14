package com.yangyang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.Link;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();


    ResponseResult getLinkListPage(Integer pageNum, Integer pageSize, String name, String status);
}
