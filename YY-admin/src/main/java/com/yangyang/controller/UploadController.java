package com.yangyang.controller;


import com.yangyang.domain.ResponseResult;
import com.yangyang.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestParam("img") MultipartFile multipartFile) {

        return uploadService.uploadImg(multipartFile);

    }
}
