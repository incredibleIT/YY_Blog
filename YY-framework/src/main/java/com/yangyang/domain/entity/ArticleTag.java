package com.yangyang.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value="sg_article_tag")
public class ArticleTag {
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 标签id
     */
    private Long tagId;

}
