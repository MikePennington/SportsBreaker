package com.breaker.utils;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class Constants {

    // These aren't final because they are initialized on startup
    public static boolean      IS_PROD;
    public static String       PICTURE_BASE_PATH;
    public static String       PICTURE_BASE_URL;
    public static String       PICTURE_USERPIC_PATH;
    public static String       PICTURE_USERPIC_URL;

    public static final String PARAM_CATEGORY_ID       = "catId";
    public static final String PARAM_START_POSIT       = "start";
    public static final String PARAM_STORY_ID          = "storyId";
    public static final String PARAM_TEAM_ID           = "teamId";
    public static final String PARAM_USER_ID           = "userId";
    public static final String PARAM_SORT_BY           = "sort";
    public static final String PARAM_SHOW_DATES        = "show";
    public static final String PARAM_USER_ACTION       = "userAction";
    public static final String PARAM_URL               = "url";

    public static final String USER_ACTION_BROKEN      = "broken";
    public static final String USER_ACTION_VOTED_ON    = "voted";
    public static final String USER_ACTION_THUMBS_UP   = "thmup";
    public static final String USER_ACTION_THUMBS_DOWN = "thmdn";

    public static final int    STAT_TYPE_VIEW_DETAIL   = 1;
    public static final int    STAT_TYPE_THUMBS_UP     = 2;
    public static final int    STAT_TYPE_THUMBS_DOWN   = 3;
    public static final int    STAT_TYPE_VISIT_URL     = 4;
    public static final int    STAT_TYPE_COMMENT       = 5;

    public static final int    MILLIS_SECOND           = 1000;
    public static final int    MILLIS_MINUTE           = MILLIS_SECOND * 60;
    public static final int    MILLIS_HOUR             = MILLIS_MINUTE * 60;
    public static final int    MILLIS_24_HOURS         = MILLIS_HOUR * 24;
    public static final int    MILLIS_WEEK             = MILLIS_24_HOURS * 7;
    public static final int    MILLIS_30_DAYS          = MILLIS_24_HOURS * 30;

    public static final String CAPTCHA_PRIVATE_KEY     = "6Le61QIAAAAAANVLItEEcfCWYx6nvHB-WBEQ-774";
    public static final String CAPTCHA_PUBLIC_KEY      = "6Le61QIAAAAAAK4MmhcPBCfqCwI_1T4a2qU7oDsA";

    public static void initializeVariables(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        if (url.indexOf("8080") == -1)
            IS_PROD = true;
        else
            IS_PROD = false;

        // Setting these here to make sure they get set correctly
        PICTURE_BASE_PATH = IS_PROD ? "/home/brkbrk1/public_html/resources/" : "C:\\Development\\resources\\";
        PICTURE_BASE_URL = IS_PROD ? "http://www.sportsbreaker.com/resources/" : "http://localhost:8080/resources/";
        PICTURE_USERPIC_PATH = PICTURE_BASE_PATH + "userpics" + File.separator;
        PICTURE_USERPIC_URL = PICTURE_BASE_URL + "userpics/";
    }

}
