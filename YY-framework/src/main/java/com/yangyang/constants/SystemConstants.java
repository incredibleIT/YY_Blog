package com.yangyang.constants;

public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 友链状态为审核通过
     */
    public static final String  LINK_STATUS_NORMAL = "0";

    public static final String MENU = "C";
    public static final String BUTTON = "F";
    /** 正常状态 */
    public static final String NORMAL = "0";
    public static final String ADMAIN = "1";

    public static final int COMMENT_ROOT_USER_ID = -1;


    public static final String ARTICLE_COMMENT = "0";

    public static final String LINK_COMMENT = "1";


    public static final String  REDIS_KEY = "article:viewCount";

    public static final int  REDIS_INCREMENT_STEP = 1;
    
}