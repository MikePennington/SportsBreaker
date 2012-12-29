package com.breaker.ajax;

import static com.breaker.utils.Constants.PARAM_STORY_ID;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.newsstory.StoryBean;
import com.breaker.newsstory.StoryFactory;
import com.breaker.newsstory.StoryStatsFactory;
import com.breaker.user.LoginUtils;
import com.breaker.user.User;
import com.breaker.utils.Constants;
import com.breaker.utils.RequestUtils;

public class ClickHelper extends AjaxAbstract {

    private static final long serialVersionUID = 1L;

    @Override
    public void doAjax(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int storyId = RequestUtils.getParameterAsInt(request, PARAM_STORY_ID, 0);
        User user = LoginUtils.getUserObject(request, response);
        StoryBean story = StoryFactory.getStoryById(storyId);
        if (story != null) {
            long userId = user != null ? user.getUserId() : 0;
            StoryStatsFactory.saveStat(storyId, userId, Constants.STAT_TYPE_VISIT_URL);
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(story.getUrl());
        }
    }
}
