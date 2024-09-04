package com.yangyang.scheduler;


import com.yangyang.constants.SystemConstants;
import com.yangyang.domain.entity.Article;
import com.yangyang.service.ArticleService;
import com.yangyang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateDBViewCountScheduler {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    //定时器
    @Scheduled(cron = "0 */10 * * * *")
    public void updateViewCount() {
        // 从redis获取数据

        Map<String, Integer> cacheMap = redisCache.getCacheMap(SystemConstants.REDIS_KEY);

        List<Article> articles = cacheMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());



        //每隔十分钟同步进DB
        articleService.updateBatchById(articles);
    }
}
