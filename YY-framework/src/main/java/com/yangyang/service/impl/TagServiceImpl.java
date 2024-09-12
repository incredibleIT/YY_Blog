package com.yangyang.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.dto.TagListDto;
import com.yangyang.domain.entity.Tag;
import com.yangyang.domain.vo.PageVo;
import com.yangyang.domain.vo.TagListVo;
import com.yangyang.domain.vo.TagVo;
import com.yangyang.mapper.TagMapper;
import com.yangyang.service.TagService;
import com.yangyang.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //根据标签名 或 备注查询标签
        LambdaQueryWrapper<Tag> tagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tagLambdaQueryWrapper.eq(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        tagLambdaQueryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());

        Page<Tag> tagPage = new Page<>(pageNum, pageSize);

        this.page(tagPage, tagLambdaQueryWrapper);

        List<Tag> records = tagPage.getRecords();
        List<TagListVo> tagListVos = BeanCopyUtils.copyBeanList(records, TagListVo.class);

        return ResponseResult.okResult(new PageVo(tagListVos, tagPage.getTotal()));

    }

    @Override
    public ResponseResult insertTag(Tag tag) {

        this.save(tag);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deletTag(Long id) {
        this.removeById(id);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagInfo(Long id) {
        LambdaQueryWrapper<Tag> tagLambdaQueryWrapper = new LambdaQueryWrapper<>();

        tagLambdaQueryWrapper.eq(Tag::getId, id);

        Tag tag = this.getById(tagLambdaQueryWrapper);

        TagListVo tagListVo = BeanCopyUtils.copyBean(tag, TagListVo.class);

        return ResponseResult.okResult(tagListVo);

    }

    @Override
    public ResponseResult alertTagInfo(Tag tag) {
        this.updateById(tag);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        List<Tag> list = this.list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);

        return ResponseResult.okResult(tagVos);
    }
}
