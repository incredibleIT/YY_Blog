package com.yangyang.service;

import com.yangyang.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {


    ResponseResult uploadImg(MultipartFile img);
}
