package com.breaker.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.newsstory.StoryStatsFactory;
import com.breaker.user.LoginUtils;
import com.breaker.user.User;
import com.breaker.utils.Constants;
import com.breaker.utils.RequestUtils;

public class VoteHelper extends AjaxAbstract {

    private static final long serialVersionUID = 1L;

    public void doAjax(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int storyId = RequestUtils.getParameterAsInt(request, "storyId", 0);
        String vote = RequestUtils.getParameterAsString(request, "vote", "");
        User user = LoginUtils.getUserObject(request, response);

        String error = null;
        if (user != null && (user.isSoftLoggedIn() || user.isHardLoggedIn())) {
            boolean updated = false;
            if ("up".equals(vote))
                updated = StoryStatsFactory.saveStat(storyId, user.getUserId(),
                        Constants.STAT_TYPE_THUMBS_UP);
            else if ("down".equals(vote))
                updated = StoryStatsFactory.saveStat(storyId, user.getUserId(),
                        Constants.STAT_TYPE_THUMBS_DOWN);
            if (!updated)
                error = "Error updating vote!";
        } else {
            error = "You must be logged in to vote!";
        }

        if (error != null) {
            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(error);
        }
    }
}
