package com.yangyang.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;

import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.yangyang.domain.ResponseResult;
import com.yangyang.enums.AppHttpCodeEnum;
import com.yangyang.exception.SystemException;
import com.yangyang.service.UploadService;
import com.yangyang.utils.PathUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


@Service
public class UploadServiceImpl implements UploadService {

    @Value("${oss.accessKey}")
    private String accessKey;
    @Value("${oss.secretKey}")
    private String secretKey;
    @Value("${oss.bucket}")
    private String bucket;




    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //进行相关监测
        String originalFilename = img.getOriginalFilename();

        if(!StringUtils.hasText(originalFilename) || !originalFilename.endsWith(".png")){
            throw new SystemException(AppHttpCodeEnum.MUST_PNG);
        }

        System.out.println(originalFilename);

        //上传
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img, filePath);

        //返回外链
        return ResponseResult.okResult(url);

    }



    private String uploadOss(MultipartFile img, String keyValue) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);


        //ak: Djy42QjGqE0aClx_wPu9QcMdCYDtyPfr9yzYsI8G
        //sk: lW0NiDJgCE3ZH4DFaJqIKgxbAeV53EOwBz0FQkwP
        //buket: yyblogss

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = keyValue;

        try {
            InputStream fileInputStream = img.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(fileInputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);

                return "http://siyijfj2m.hb-bkt.clouddn.com/" + key;
            } catch (QiniuException ex) {
                ex.printStackTrace();
                if (ex.response != null) {
                    System.err.println(ex.response);

                    try {
                        String body = ex.response.toString();
                        System.err.println(body);
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ex) {
            //ignore
        }

        return "www";

    }



    private String uploadFile(String filePath, InputStream inputStream) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "oss-cn-beijing.aliyuncs.com";

        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "weqeq";
        String accessKeySecret = "wqeqrqrqwe";
        String bucketName = "sg-blog-oss";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建PutObjectRequest对象。
        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, inputStream);

        // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传字符串。
        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
        return "https://"+bucketName+"."+endpoint+"/"+filePath;
    }
}
