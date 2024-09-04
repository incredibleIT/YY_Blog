package com.yangyang.controller;


import com.yangyang.annotation.AspectAnnotation;
import com.yangyang.domain.ResponseResult;
import com.yangyang.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("link")
@Api(tags = "友链", description = "友链功能相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;


    @ApiOperation(value = "友链列表", notes = "获取全部友链信息列表")
    @GetMapping("/getAllLink")
    @AspectAnnotation(businessName = "获取全部友链信息列表")
    public ResponseResult getAllLink() {

        return linkService.getAllLink();


    }





}
