package com.yangyang.controller;


import com.yangyang.annotation.AspectAnnotation;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.dto.TagListDto;
import com.yangyang.domain.entity.Tag;
import com.yangyang.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class TagController {


    @Autowired
    private TagService tagService;


    @PostMapping("/content/tag")
    public ResponseResult insertTag(@RequestBody Tag tag) {

        return tagService.insertTag(tag);

    }

    @DeleteMapping("/content/tag/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id) {

        return tagService.deletTag(id);

    }

    // 获取标签信息
    @GetMapping("/content/tag/{id}")
    public ResponseResult getTagInfo(@PathVariable("id") Long id) {
        return tagService.getTagInfo(id);
    }

    // 修改标签
    @PutMapping("/content/tag/{id}")
    public ResponseResult alertTagInfo(@RequestBody Tag tag) {
        return tagService.alertTagInfo(tag);
    }



    @AspectAnnotation(businessName = "根据标签名或备注查询标签")
    @GetMapping("content/tag/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {


        return tagService.pageTagList(pageNum, pageSize, tagListDto);

    }

    @GetMapping("/content/tag/listAllTag")
    public ResponseResult listAllTag() {
        return tagService.listAllTag();
    }
}
