package com.breaker.utils;

import static com.breaker.utils.Constants.PARAM_STORY_ID;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.newsstory.StoryBean;
import com.breaker.newsstory.StoryFactory;
import com.breaker.newsstory.StoryStatsFactory;
import com.breaker.user.LoginUtils;
import com.breaker.user.User;

public class ClickServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        int storyId = RequestUtils.getParameterAsInt(request, PARAM_STORY_ID, 0);
        User user = LoginUtils.getUserObject(request, response);
        StoryBean story = StoryFactory.getStoryById(storyId);
        if (story != null) {
            long userId = user != null ? user.getUserId() : 0;
            StoryStatsFactory.saveStat(storyId, userId, Constants.STAT_TYPE_VISIT_URL);
            response.sendRedirect(story.getUrl());
        }
    }

}
