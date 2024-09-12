package com.yangyang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.domain.entity.ArticleTag;
import com.yangyang.mapper.ArticleTagMapper;
import com.yangyang.service.ArticleTagService;
import org.springframework.stereotype.Service;


@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
}
