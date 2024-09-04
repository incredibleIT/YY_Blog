package com.yangyang.controller;


import com.yangyang.annotation.AspectAnnotation;
import com.yangyang.domain.ResponseResult;
import com.yangyang.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Api(tags = "文件上传", description = "文件上传相关接口")
public class UploadController {

    @Autowired
    private UploadService uploadService;



    @ApiOperation(value = "图片上传", notes = "上传图片接口")
    @PostMapping("/upload")
    @AspectAnnotation(businessName = "上传图片接口")
    @ApiImplicitParam(name = "img", value = "图片对象")
    public ResponseResult uploadImg(MultipartFile img) {

            return uploadService.uploadImg(img);

    }
}
