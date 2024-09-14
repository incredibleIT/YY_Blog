package com.yangyang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.constants.SystemConstants;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.Link;
import com.yangyang.domain.vo.LinkVo;
import com.yangyang.domain.vo.PageVo;
import com.yangyang.mapper.LinkMapper;
import com.yangyang.service.LinkService;
import com.yangyang.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Override
    public ResponseResult getAllLink() {

        LambdaQueryWrapper<Link> linkQueryWrapper = new LambdaQueryWrapper<>();

        linkQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);

        List<Link> list = this.list(linkQueryWrapper);

        System.out.println(list);

        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(list, LinkVo.class));
    }

    @Override
    public ResponseResult getLinkListPage(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();

        linkLambdaQueryWrapper.like(StringUtils.hasText(name), Link::getName, name);
        linkLambdaQueryWrapper.eq(StringUtils.hasText(status), Link::getStatus, status);

        Page<Link> linkPage = new Page<>(pageNum, pageSize);

        this.page(linkPage, linkLambdaQueryWrapper);

        return ResponseResult.okResult(new PageVo(linkPage.getRecords(), linkPage.getTotal()));

    }
}
