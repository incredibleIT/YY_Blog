package com.yangyang.runner;

import com.yangyang.domain.entity.Article;
import com.yangyang.mapper.ArticleMapper;
import com.yangyang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;


    @Autowired
    private RedisCache redisCache;


    @Override
    public void run(String... args) throws Exception {


        //在数据库查询所有文章信息
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> articleMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));

        //存入rides
        redisCache.setCacheMap("article:viewCount", articleMap);

    }
}
