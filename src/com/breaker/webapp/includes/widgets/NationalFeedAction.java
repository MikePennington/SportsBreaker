package com.breaker.webapp.includes.widgets;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.newsstory.StoryStatsFactory;
import com.breaker.rss.FeedBrokerParameters;
import com.breaker.rss.FeedItemInfo;
import com.breaker.rss.FeedBroker;
import com.breaker.team.TeamBean;
import com.breaker.team.TeamDBUtils;
import com.breaker.user.LoginUtils;
import com.breaker.user.User;
import com.breaker.utils.Constants;
import com.breaker.utils.RequestUtils;
import com.breaker.webapp.MVCActionInterface;
import com.breaker.webapp.MVCRedirector;

public class NationalFeedAction implements MVCActionInterface {

    private static final int NUM_PER_PAGE = 10;

    public MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response) {
        NationalFeedsView view = new NationalFeedsView();

        FeedBrokerParameters params = new FeedBrokerParameters();
        params.setReturnBrokenById(true);

        int teamId = RequestUtils.getParameterAsInt(request, Constants.PARAM_TEAM_ID, 0);
        TeamBean team = TeamDBUtils.getTeamById(teamId);
        params.setTeamId(teamId);
        if (team != null && team.getCollegeId() > 0)
            params.setCollegeId(team.getCollegeId());

        // Find category id. Default to 1.
        int categoryId = RequestUtils.getParameterAsInt(request, Constants.PARAM_CATEGORY_ID, 1);
        categoryId = categoryId < 1 ? 1 : categoryId;
        params.setCategoryId(categoryId);

        int startPosition = RequestUtils.getParameterAsInt(request, "rssStart", 0);
        params.setStartPosition(startPosition);

        List<FeedItemInfo> rssItems = findRssItems(team, params);

        User user = LoginUtils.getUserObject(request, response);
        for (int i = 0; rssItems != null && i < rssItems.size(); i++) {
            long userId = user != null ? user.getUserId() : 0;
            setStatus(rssItems.get(i), userId);
        }

        view.setRssItems(rssItems);
        view.setLoggedIn(LoginUtils.isLoggedIn(request, response));

        if (startPosition > 0) {
            view.setShowPrevious(true);
            view.setPreviousIndex(startPosition > NUM_PER_PAGE ? startPosition - NUM_PER_PAGE : 0);
        }
        if (startPosition < FeedBrokerParameters.MAX_FEEDS_TO_RETURN - NUM_PER_PAGE) {
            view.setShowNext(true);
            view.setNextIndex(startPosition + NUM_PER_PAGE);
        }
        request.setAttribute("NationalFeedsView", view);
        return null;
    }

    /**
     * If there are no results by team, try results by college. If there are no
     * results by college, or college is not available, try getting results by
     * sport.F
     * 
     * @param team
     * @param params
     * @return
     */
    private List<FeedItemInfo> findRssItems(TeamBean team, FeedBrokerParameters params) {
        List<FeedItemInfo> rssItems = FeedBroker.getFeeds(params);
        if (rssItems == null || rssItems.isEmpty()) {
            if (params.getTeamId() > 0) {
                params.setTeamId(0);
                if (team != null && team.getCollegeId() > 0) {
                    params.setCollegeId(team.getCollegeId());
                } else {
                    params.setCategoryId(team.getCategoryId());
                }
            } else if (params.getCollegeId() > 0) {
                params.setCollegeId(0);
                params.setCategoryId(team.getCategoryId());
            }
            return findRssItems(team, params);
        }
        return rssItems;
    }

    private void setStatus(FeedItemInfo item, long userId) {

        long brokenBy = item.getBrokenBy();
        if (brokenBy > 0) {
            int userVote = StoryStatsFactory.getUserVote(item.getStoryId(), userId);
            if (userVote == Constants.STAT_TYPE_THUMBS_UP)
                item.setUserStatus(FeedItemInfo.USER_STATUS_THUMBS_UP);
            else if (userVote == Constants.STAT_TYPE_THUMBS_DOWN)
                item.setUserStatus(FeedItemInfo.USER_STATUS_THUMBS_DOWN);
            else
                item.setUserStatus(FeedItemInfo.USER_STATUS_BROKEN);
        } else {
            item.setUserStatus(FeedItemInfo.USER_STATUS_NOT_BROKEN);
        }
    }
}