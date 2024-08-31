package com.yangyang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.constants.SystemConstants;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.Link;
import com.yangyang.domain.vo.LinkVo;
import com.yangyang.mapper.LinkMapper;
import com.yangyang.service.LinkService;
import com.yangyang.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

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
}
