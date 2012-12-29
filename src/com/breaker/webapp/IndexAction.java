package com.breaker.webapp;

import static com.breaker.utils.Constants.PARAM_CATEGORY_ID;
import static com.breaker.utils.Constants.PARAM_SHOW_DATES;
import static com.breaker.utils.Constants.PARAM_SORT_BY;
import static com.breaker.utils.Constants.PARAM_TEAM_ID;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.breaker.navigation.NavBuilder;
import com.breaker.navigation.NavItemBean;
import com.breaker.newsstory.CategoryBean;
import com.breaker.newsstory.CategoryFactory;
import com.breaker.newsstory.StoryBean;
import com.breaker.newsstory.StoryListFactory;
import com.breaker.newsstory.StoryListParameters;
import com.breaker.rss.output.RSS;
import com.breaker.team.TeamBean;
import com.breaker.team.TeamDBUtils;
import com.breaker.utils.StringUtils;

/**
 * Action class for index.jsp
 * 
 * @author Mike Pennington
 */
public class IndexAction implements MVCActionInterface {
    private static Logger logger              = Logger.getLogger(IndexAction.class);

    private static int    DEFAULT_NUM_STORIES = 10;
    private int           numStoriesPerPage   = 10;

    public MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response) {

        StoryListParameters params = new StoryListParameters();
        params.populateFromRequest(request);
        params.setNumStoriesToReturn(DEFAULT_NUM_STORIES);
        int endPosit = params.getStartPosition() + numStoriesPerPage - 1;

        List<StoryBean> stories = StoryListFactory.getStoryList(params);
        // List<StoryBean> stories = new ArrayList<StoryBean>();
        logger.debug("Num stories: " + stories.size());

        int storiesCount = StoryListFactory.getStoryListCount(params);

        IndexView view = new IndexView();
        view.setCategoryId(params.getCategoryId());
        view.setTeamId(params.getTeamId());
        view.setSortBy(params.getSortBy());
        view.setShowDates(params.getShowDates());
        view.setStories(stories);
        view.setStartPosit(params.getStartPosition());
        view.setEndPosit(endPosit);
        view.setNavList(getNavigation(params.getStartPosition(), storiesCount, getBaseURL(params.getTeamId(), params
                .getCategoryId(), params.getSortBy(), params.getShowDates())));
        view.setRssTitle(getRSSTitle(params.getCategoryId(), params.getTeamId()));
        view.setRssFeedTitle(RSS.makePageTitle(params));
        view.setPageTitle(makePageTitle(params.getCategoryId(), params.getTeamId()));

        String baseUriForResorting = "/index.jsp?" + PARAM_CATEGORY_ID + "=" + params.getCategoryId() + "&"
                + PARAM_TEAM_ID + "=" + params.getTeamId();
        view.setBaseUriForResorting(baseUriForResorting);

        // Build link to RSS feed
        StringBuilder rssLink = new StringBuilder("/rss/");
        StringUtils.appendParameter(rssLink, PARAM_CATEGORY_ID, params.getCategoryId());
        StringUtils.appendParameter(rssLink, PARAM_TEAM_ID, params.getTeamId());
        StringUtils.appendParameter(rssLink, PARAM_SORT_BY, params.getSortBy());
        StringUtils.appendParameter(rssLink, PARAM_SHOW_DATES, params.getShowDates());
        view.setRssLink(rssLink.toString());

        request.setAttribute("IndexView", view);

        return null;
    }

    private List<NavItemBean> getNavigation(int currentPosition, int totalNumElements, String baseURL) {
        NavBuilder navBuilder = new NavBuilder();
        navBuilder.setBaseURL(baseURL);
        navBuilder.setCurrentPosition(currentPosition);
        navBuilder.setItemsPerPage(numStoriesPerPage);
        navBuilder.setTotalNumElements(totalNumElements);
        return navBuilder.buidNavList();
    }

    private String getBaseURL(int teamId, int categoryId, int sort, int show) {
        StringBuilder baseURL = new StringBuilder("index.jsp");
        if (teamId > 0)
            StringUtils.appendParameter(baseURL, PARAM_TEAM_ID, teamId);

        if (categoryId > 0) {
            StringUtils.appendParameter(baseURL, PARAM_CATEGORY_ID, categoryId);
        }

        if (sort > 0) {
            StringUtils.appendParameter(baseURL, PARAM_SORT_BY, sort);
        }

        if (show > 0) {
            StringUtils.appendParameter(baseURL, PARAM_SHOW_DATES, show);
        }

        return baseURL.toString();
    }

    private String getRSSTitle(int categoryId, int teamId) {
        String ender = " News Feed";
        TeamBean team = TeamDBUtils.getTeamById(teamId);
        if (team != null) {
            return team.getNoSportDisplayName() + ender;
        }
        CategoryBean category = CategoryFactory.getCategoryById(categoryId);
        if (category != null) {
            return category.getName() + ender;
        }
        return "National " + ender;
    }

    private String makePageTitle(int categoryId, int teamId) {
        StringBuilder pageTitle = new StringBuilder("SportsBreaker - ");
        if (teamId > 0) {
            TeamBean team = TeamDBUtils.getTeamById(teamId);
            if (team != null) {
                pageTitle.append(team.getDisplayName() + " News Stories");
            }
        } else if (categoryId > 1) {
            CategoryBean cat = CategoryFactory.getCategoryById(categoryId);
            if (cat != null) {
                pageTitle.append(cat.getName() + " News Stories");
            }
        } else {
            pageTitle.append("Sports News Stories from NFL, MLB, NBA, NHL, College Sports and More");
        }
        return pageTitle.toString();
    }

}
