package com.yangyang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.dto.TagListDto;
import com.yangyang.domain.entity.Tag;
import com.yangyang.domain.vo.TagListVo;

public interface TagService extends IService<Tag> {
    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult insertTag(Tag tag);

    ResponseResult deletTag(Long id);

    ResponseResult getTagInfo(Long id);

    ResponseResult alertTagInfo(Tag tag);

    ResponseResult listAllTag();

}
