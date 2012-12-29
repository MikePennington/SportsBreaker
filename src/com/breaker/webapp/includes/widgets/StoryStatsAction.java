package com.breaker.webapp.includes.widgets;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.newsstory.StoryBean;
import com.breaker.newsstory.StoryFactory;
import com.breaker.newsstory.StoryStatsBean;
import com.breaker.newsstory.StoryStatsFactory;
import com.breaker.user.LoginUtils;
import com.breaker.user.User;
import com.breaker.utils.RequestUtils;
import com.breaker.webapp.MVCActionInterface;
import com.breaker.webapp.MVCRedirector;

/**
 * Action class for /includes/widgets/storyStats.jsp
 * 
 * @author Mike Pennington
 */
public class StoryStatsAction implements MVCActionInterface {

    public MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response) {
        StoryStatsView view = new StoryStatsView();

        StoryBean story = (StoryBean) request.getAttribute("story");
        if (story == null) {
            int storyId = RequestUtils.getParameterAsInt(request, "storyId", 0);
            if (storyId > 0) {
                story = StoryFactory.getStoryById(storyId);
            }
        }
        if (story == null) {
            view.setScore("N/A");
            view.setThumbsUp("N/A");
            view.setThumbsDown("N/A");
            view.setCurrentActivityRating("N/A");
            view.setVotes("N/A");
            request.setAttribute("StoryStatsView", view);
            return null;
        }

        long userId = RequestUtils.getAttributeAsLong(request, "userId", 0);
        if (userId < 1) {
            User user = LoginUtils.getUserObject(request, response);
            if (user != null)
                userId = user.getUserId();
        }

        int userVote = 0;
        if (userId > 0)
            userVote = StoryStatsFactory.getUserVote(story.getStoryId(), userId);

        StoryStatsBean stats = story.getStats();

        view.setStoryId(story.getStoryId());
        view.setScore(String.valueOf(stats.getScore()));
        view.setThumbsUp(String.valueOf(stats.getThumbsUp()));
        view.setThumbsDown(String.valueOf(stats.getThumbsDown()));
        view.setDetailViews(String.valueOf(stats.getDetailViews()));
        view.setUrlViews(String.valueOf(stats.getUrlViews()));
        view.setVotes(String.valueOf(stats.getThumbsUp() + stats.getThumbsDown()));
        view.setLoggedIn(LoginUtils.isLoggedIn(request, response));

        DecimalFormat df = new DecimalFormat("#,###.##");
        view.setCurrentActivityRating(String.valueOf(df.format(stats.getCurrentActivityRating())));
        view.setUserVote(userVote);

        request.setAttribute("StoryStatsView", view);
        return null;
    }

}