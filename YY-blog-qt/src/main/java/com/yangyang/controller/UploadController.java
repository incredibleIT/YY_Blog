package com.yangyang.controller;


import com.yangyang.domain.ResponseResult;
import com.yangyang.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;


    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img) {

            return uploadService.uploadImg(img);

    }
}
