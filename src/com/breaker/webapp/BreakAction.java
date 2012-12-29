package com.breaker.webapp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.newsstory.CategoryBean;
import com.breaker.newsstory.CategoryFactory;
import com.breaker.newsstory.StoryBean;
import com.breaker.newsstory.StoryFactory;
import com.breaker.rss.FeedBroker;
import com.breaker.rss.FeedItemInfo;
import com.breaker.team.TeamBean;
import com.breaker.team.TeamDBUtils;
import com.breaker.user.LoginUtils;
import com.breaker.user.User;
import com.breaker.utils.Constants;
import com.breaker.utils.IOUtils;
import com.breaker.utils.RequestUtils;
import com.breaker.utils.StringUtils;

/**
 * Action class for break.jsp
 * 
 * @author Mike Pennington
 */
public class BreakAction implements MVCActionInterface {

    private static final int    MIN_TITLE_LENGTH       = 5;
    private static final int    MAX_TITLE_LENGTH       = 75;
    private static final int    MIN_DESCRIPTION_LENGTH = 15;
    private static final int    MAX_DESCRIPTION_LENGTH = 500;
    private static final String PARAM_SPORT            = "sport";
    private static final String PARAM_SPORT_HIDDEN     = "sportHidden";
    private static final String PARAM_TEAM1            = "team_1";
    private static final String PARAM_TITLE            = "title";
    private static final String PARAM_DESCRIPTION      = "desc";
    private static final String PARAM_RSS_ID           = "rssId";
    private static final String PARAM_SELETED_TEAM_IDS = "selectedTeamIds";
    private static final String PARAM_CONFERENCE       = "conference";

    public MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response) {
        BreakView view = new BreakView();
        if (!LoginUtils.isLoggedIn(request, response)) {
            MVCRedirector redirector = new MVCRedirector();
            String thisUrl = StringUtils.encodeUrl(RequestUtils.getFullUrl(request));
            redirector.setRedirectUrl((new StringBuilder("login.jsp?returnUrl=")).append(thisUrl).toString());
            return redirector;
        }

        if (!StringUtils.isEmpty(request.getParameter("breakform_submit"))
                || !StringUtils.isEmpty(request.getParameter("checkDuplicate"))) {
            String errors = validateForm(request);

            if (!StringUtils.isEmpty(errors)) {
                view.setError(errors);
            } else if (!StringUtils.isEmpty(request.getParameter("breakform_submit"))) {
                User user = LoginUtils.getUserObject(request, response);
                StoryBean story = buildStoryBeanFromRequest(request, user);
                int storyId = StoryFactory.saveNewStoryToDB(story);
                if (storyId > 0) {
                    MVCRedirector redirector = new MVCRedirector();
                    redirector.setRedirectUrl("story.jsp?id=" + storyId);
                    return redirector;
                }
                view.setError("There was an unknown problem breaking this story. Please try again");
            }
        }

        // Populate items from the RSS feed, if available
        int rssId = RequestUtils.getParameterAsInt(request, PARAM_RSS_ID, 0);
        if (rssId > 0) {
            FeedItemInfo item = FeedBroker.getFeedInfoItem(rssId);
            if (item != null) {
                view.setUrl(StringUtils.unNull(item.getLink()));
                view.setTitle(StringUtils.unNull(item.getTitle()));
                view.setDefaultCategoryId(item.getCategoryId());
            }
        }

        int categoryId = getCategoryIdFromRequest(request);
        String selectedTeamIds = request.getParameter(PARAM_SELETED_TEAM_IDS);
        List<TeamBean> selectedTeams = buildTeamListFromSelectedTeamIds(selectedTeamIds, 0);

        String newConSubCon = RequestUtils.getParameterAsString(request, PARAM_CONFERENCE, null);
        if (!StringUtils.isEmpty(newConSubCon) && RequestUtils.getParameterAsBoolean(request, "allInConference", false)) {
            int index = newConSubCon.indexOf("---");
            String newConference = index > -1 ? newConSubCon.substring(0, index) : newConSubCon;
            String newSubconference = index > -1 ? newConSubCon.substring(index + 3, newConSubCon.length()) : null;
            List<TeamBean> teams = TeamDBUtils.listByCategoryAndConference(categoryId, newConference, newSubconference);
            for (TeamBean team : teams) {
                if (!selectedTeams.contains(team)) {
                    selectedTeamIds = selectedTeamIds + "," + team.getTeamId();
                    selectedTeams.add(team);
                }
            }
        } else if (RequestUtils.getParameterAsBoolean(request, "allInSport", false)) {
            List<TeamBean> teams = TeamDBUtils.listByCategoryAndConference(categoryId, null, null);
            for (TeamBean team : teams) {
                if (!selectedTeams.contains(team)) {
                    selectedTeamIds = selectedTeamIds + "," + team.getTeamId();
                    selectedTeams.add(team);
                }
            }
        }

        int newTeamId = RequestUtils.getParameterAsInt(request, PARAM_TEAM1, 0);
        if (newTeamId > 0) {
            TeamBean team = TeamDBUtils.getTeamById(newTeamId);
            if (!selectedTeams.contains(team)) {
                selectedTeamIds = selectedTeamIds + "," + team.getTeamId();
                selectedTeams.add(team);
            }
        }

        CategoryFactory categoryFactory = new CategoryFactory();
        categoryFactory.setReturnNationalCategory(false);
        List<CategoryBean> categories = categoryFactory.getDisplayCategories(CategoryFactory.ORDER_BY_NAME);
        if (view.getDefaultCategoryId() == 0)
            view.setSelectedCategoryId(categoryId);
        view.setCategories(categories);
        view.setSelectedTeamIds(selectedTeamIds);
        view.setSelectedTeams(selectedTeams);
        view.setUrl(RequestUtils.getParameterAsString(request, Constants.PARAM_URL, view.getUrl()));
        view.setTitle(RequestUtils.getParameterAsString(request, PARAM_TITLE, view.getTitle()));
        view.setDescription(RequestUtils.getParameterAsString(request, PARAM_DESCRIPTION, view.getDescription()));
        request.setAttribute("BreakView", view);
        return null;
    }

    /**
     * Validates the form input
     * 
     * @param request
     * @return
     */
    private String validateForm(HttpServletRequest request) {
        StringBuilder errors = new StringBuilder();
        boolean isCheckDuplicate = request.getParameter("checkDuplicate") != null;

        String urlString = request.getParameter(Constants.PARAM_URL);
        if (!isCheckDuplicate) {
            if (StringUtils.isEmpty(urlString))
                appendError(errors, "Please enter a valid URL.");

            // Test URL
            if (!urlString.startsWith("http://"))
                urlString = "http://" + urlString;
            if (!IOUtils.isValidURL(urlString))
                appendError(errors, "The URL you specified appears to be invalid.");

            String title = request.getParameter(PARAM_TITLE);
            if (StringUtils.isEmpty(title))
                appendError(errors, "Please enter a valid title.");
            else if (title.length() < MIN_TITLE_LENGTH)
                appendError(errors, "The title must be at least " + MIN_TITLE_LENGTH + " characters long.");
            else if (title.length() > MAX_TITLE_LENGTH)
                appendError(errors, "The title cannot be longer then " + MAX_TITLE_LENGTH + " characters.");

            String description = request.getParameter(PARAM_DESCRIPTION);
            if (StringUtils.isEmpty(description))
                appendError(errors, "Please enter a valid description.");
            else if (description.length() < MIN_DESCRIPTION_LENGTH)
                appendError(errors, "The description must be at least " + MIN_DESCRIPTION_LENGTH + " characters long.");
            else if (description.length() > MAX_DESCRIPTION_LENGTH)
                appendError(errors, "The description must cannot be more than " + MAX_DESCRIPTION_LENGTH
                        + " characters long. Right now your description is " + description.length()
                        + " characters long.");
        }

        StoryBean duplicate = StoryFactory.findByURL(urlString);
        if (duplicate != null) {
            appendError(errors, "This seems to be a duplicate of: <a href=\"story.jsp?id=" + duplicate.getStoryId()
                    + "\">" + duplicate.getTitle() + "</a>");
        } else if (isCheckDuplicate)
            appendError(errors, "Congrats. It looks like you are the first person to submit this story.");
        return errors.toString();
    }

    /**
     * Appends the given String to the String StringBuilder in an HTML friendly
     * way.
     * 
     * @param sb
     * @param s
     */
    private void appendError(StringBuilder sb, String s) {
        if (sb == null)
            sb = new StringBuilder();
        if (sb.length() == 0)
            sb.append(s);
        else
            sb.append("<br>" + s);
    }

    private List<TeamBean> buildTeamListFromSelectedTeamIds(String selectedTeamIds, int teamIdInDropDown) {
        List<TeamBean> selectedTeams = new ArrayList<TeamBean>();
        if (!StringUtils.isEmpty(selectedTeamIds)) {
            String[] selectedTeamIdArr = selectedTeamIds.split(",");
            Collection<Integer> teamIds = new ArrayList<Integer>();
            for (String teamIdStr : selectedTeamIdArr) {
                int teamId = StringUtils.toInt(teamIdStr);
                if (teamId > 0)
                    teamIds.add(new Integer(teamId));
            }
            if (!teamIds.isEmpty())
                selectedTeams = TeamDBUtils.listTeamsByIds(teamIds);
        } else if (teamIdInDropDown > 0) {
            TeamBean team = TeamDBUtils.getTeamById(teamIdInDropDown);
            if (team != null) {
                selectedTeams.add(team);
            }
        }
        return selectedTeams;
    }

    private StoryBean buildStoryBeanFromRequest(HttpServletRequest request, User user) {
        StoryBean story = new StoryBean();
        if (user != null)
            story.setBrokenByUser(user);
        story.setTitle(request.getParameter(PARAM_TITLE));
        story.setDescription(request.getParameter(PARAM_DESCRIPTION));
        story.setBrokenDate(new Date());
        story.setRssId(RequestUtils.getParameterAsLong(request, PARAM_RSS_ID, 0L));

        String urlString = request.getParameter(Constants.PARAM_URL);
        if (!urlString.startsWith("http://"))
            urlString = "http://" + urlString;
        story.setUrl(urlString);

        CategoryBean categoryBean = CategoryFactory.getCategoryById(getCategoryIdFromRequest(request));
        story.addCategory(categoryBean);

        String selectedTeamIds = request.getParameter(PARAM_SELETED_TEAM_IDS);
        int teamIdInDropDown = RequestUtils.getParameterAsInt(request, PARAM_TEAM1, 0);
        List<TeamBean> teams = buildTeamListFromSelectedTeamIds(selectedTeamIds, teamIdInDropDown);
        story.setTeams(teams);

        return story;
    }

    private int getCategoryIdFromRequest(HttpServletRequest request) {
        int categoryId = RequestUtils.getParameterAsInt(request, PARAM_SPORT, 0);
        if (categoryId <= 0) {
            categoryId = RequestUtils.getParameterAsInt(request, PARAM_SPORT_HIDDEN, 0);
        }
        return categoryId;
    }
}