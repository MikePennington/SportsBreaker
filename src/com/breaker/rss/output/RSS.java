package com.breaker.rss.output;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.newsstory.CategoryBean;
import com.breaker.newsstory.CategoryFactory;
import com.breaker.newsstory.StoryBean;
import com.breaker.newsstory.StoryListFactory;
import com.breaker.newsstory.StoryListParameters;
import com.breaker.team.TeamBean;
import com.breaker.team.TeamDBUtils;
import com.breaker.user.User;
import com.breaker.user.UserManager;
import com.breaker.utils.XMLUtils;

public class RSS extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int  NUM_STORIES      = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        StoryListParameters params = new StoryListParameters();
        params.populateFromRequest(request);
        params.setNumStoriesToReturn(NUM_STORIES);
        params.setCountComments(false);
        params.setCountComments(false);
        params.setPopulateTeams(false);
        params.setPopulateCategories(false);

        List<StoryBean> stories = StoryListFactory.getStoryList(params);
        // List<StoryBean> stories = new ArrayList<StoryBean>();

        StringBuilder rss = new StringBuilder();
        rss.append("<?xml version=\"1.0\"?>");
        rss.append("<rss version=\"2.0\">");

        rss.append(XMLUtils.openTag("channel"));
        rss.append(XMLUtils.buildTextNode("title", makePageTitle(params)));
        rss.append(XMLUtils.buildTextNode("link", "http://www.sportsbreaker.com/"));
        rss.append(XMLUtils.buildTextNode("description", "Sports Stories"));
        rss.append(XMLUtils.buildTextNode("language", "en-us"));
        rss.append(XMLUtils.buildTextNode("ttl", "5"));

        for (StoryBean story : stories) {
            String landingPage = "http://www.sportsbreaker.com/story.jsp?id=" + story.getStoryId();
            rss.append(XMLUtils.openTag("item"));
            rss.append(XMLUtils.buildTextNode("title", story.getTitle()));
            rss.append(XMLUtils.buildTextNode("link", landingPage));
            rss.append(XMLUtils.buildTextNode("description", story.getDescription()));
            rss.append(XMLUtils.buildTextNode("pubDate", story.getBrokenDateStr()));
            rss.append(XMLUtils.buildTextNode("guid", landingPage));
            rss.append(XMLUtils.closeTag("item"));
        }

        rss.append(XMLUtils.closeTag("channel"));
        rss.append(XMLUtils.closeTag("rss"));

        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(rss.toString());
    }

    public static String makePageTitle(StoryListParameters params) {
        StringBuilder pageTitle = new StringBuilder("SportsBreaker : ");

        if (params.getTeamId() > 0) {
            TeamBean team = TeamDBUtils.getTeamById(params.getTeamId());
            if (team != null) {
                pageTitle.append(team.getDisplayName());
            }
        } else if (params.getCategoryId() > 1) {
            CategoryBean cat = CategoryFactory.getCategoryById(params.getCategoryId());
            if (cat != null) {
                pageTitle.append(cat.getName());
            }
        } else if (params.getBrokenByUserId() > 0) {
            User user = UserManager.getUserFromDB(params.getBrokenByUserId());
            if (user != null) {
                pageTitle.append("Broken by " + user.getUserName());
            }
        } else if (params.getUserIdVotedOn() > 0) {
            User user = UserManager.getUserFromDB(params.getUserIdVotedOn());
            if (user != null) {
                pageTitle.append("Voted on by " + user.getUserName());
            }
        } else if (params.getUserIdThumbsUp() > 0) {
            User user = UserManager.getUserFromDB(params.getUserIdThumbsUp());
            if (user != null) {
                pageTitle.append("Voted Thumbs-up by " + user.getUserName());
            }
        } else if (params.getUserIdThumbsDown() > 0) {
            User user = UserManager.getUserFromDB(params.getUserIdThumbsDown());
            if (user != null) {
                pageTitle.append("Voted Thumbs-down by " + user.getUserName());
            }
        } else {
            pageTitle.append("All Stories");
        }

        pageTitle.append(" : ");

        if (params.getSortBy() == StoryListParameters.SORT_BY_NEWEST) {
            pageTitle.append("Most recent");
        } else if (params.getSortBy() == StoryListParameters.SORT_BY_RATING) {
            pageTitle.append("Top ");
            if (params.getShowDates() == StoryListParameters.SHOW_DATES_24_HOURS)
                pageTitle.append("Today");
            else if (params.getShowDates() == StoryListParameters.SHOW_DATES_30_DAYS)
                pageTitle.append("this Month");
            else if (params.getShowDates() == StoryListParameters.SHOW_DATES_YEAR)
                pageTitle.append("this Year");
            else if (params.getShowDates() == StoryListParameters.SHOW_DATES_ALL)
                pageTitle.append("All Time");
        } else {
            pageTitle.append("Most popular");
        }

        return pageTitle.toString();
    }
}
