package com.breaker.newsstory;

import static com.breaker.utils.Constants.PARAM_CATEGORY_ID;
import static com.breaker.utils.Constants.PARAM_SHOW_DATES;
import static com.breaker.utils.Constants.PARAM_SORT_BY;
import static com.breaker.utils.Constants.PARAM_START_POSIT;
import static com.breaker.utils.Constants.PARAM_TEAM_ID;
import static com.breaker.utils.Constants.PARAM_USER_ACTION;
import static com.breaker.utils.Constants.PARAM_USER_ID;

import javax.servlet.http.HttpServletRequest;

import com.breaker.utils.Constants;
import com.breaker.utils.RequestUtils;
import com.breaker.utils.StringUtils;

public class StoryListParameters {

    public static final int SORT_BY_NEWEST      = 1;
    public static final int SORT_BY_RATING      = 2;
    public static final int SORT_BY_CAR         = 3;
    public static final int SHOW_DATES_ALL      = 1;
    public static final int SHOW_DATES_24_HOURS = 2;
    public static final int SHOW_DATES_30_DAYS  = 3;
    public static final int SHOW_DATES_YEAR     = 4;

    private int             sortBy              = SORT_BY_NEWEST;
    private int             showDates           = SHOW_DATES_ALL;
    private int             categoryId          = 0;
    private int             teamId              = 0;
    private long            brokenByUserId      = 0;
    private int             startPosition       = 0;

    // Only one of the following three can be set
    private long            userIdVotedOn       = 0;
    private long            userIdThumbsUp      = 0;
    private long            userIdThumbsDown    = 0;

    // 0 = return all
    private int             numStoriesToReturn  = 10;
    private boolean         populateCategories  = false;
    private boolean         populateTeams       = false;
    private boolean         countComments       = false;

    public void populateFromRequest(HttpServletRequest request) {
        setPopulateCategories(true);
        setPopulateTeams(true);

        int categoryId = RequestUtils.getParameterAsInt(request, PARAM_CATEGORY_ID, 1);
        // If catId > 1 because "national" is really "All categories"
        if (categoryId > 1)
            setCategoryId(categoryId);

        int teamId = RequestUtils.getParameterAsInt(request, PARAM_TEAM_ID, 0);
        if (teamId > 0)
            setTeamId(teamId);

        int startPosit = RequestUtils.getParameterAsInt(request, PARAM_START_POSIT, 0);

        int sortBy = RequestUtils.getParameterAsInt(request, PARAM_SORT_BY, StoryListParameters.SORT_BY_CAR);

        int showDates = RequestUtils.getParameterAsInt(request, PARAM_SHOW_DATES, StoryListParameters.SHOW_DATES_ALL);

        setSortBy(sortBy);
        setShowDates(showDates);
        setCountComments(true);
        setStartPosition(startPosit);

        long userId = RequestUtils.getParameterAsLong(request, PARAM_USER_ID, 0);
        String userAction = RequestUtils.getParameterAsString(request, PARAM_USER_ACTION, "");
        if (userId > 0 && !StringUtils.isEmpty(userAction)) {
            if (Constants.USER_ACTION_BROKEN.equals(userAction))
                setBrokenByUserId(userId);
            else if (Constants.USER_ACTION_VOTED_ON.equals(userAction))
                setUserIdVotedOn(userId);
            else if (Constants.USER_ACTION_THUMBS_UP.equals(userAction))
                setUserIdThumbsUp(userId);
            else if (Constants.USER_ACTION_THUMBS_DOWN.equals(userAction))
                setUserIdThumbsDown(userId);
        }
    }

    public long getBrokenByUserId() {
        return brokenByUserId;
    }

    public void setBrokenByUserId(long brokenByUserId) {
        this.brokenByUserId = brokenByUserId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getNumStoriesToReturn() {
        return numStoriesToReturn;
    }

    public void setNumStoriesToReturn(int numStoriesToReturn) {
        this.numStoriesToReturn = numStoriesToReturn;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public boolean isPopulateCategories() {
        return populateCategories;
    }

    public void setPopulateCategories(boolean populateCategories) {
        this.populateCategories = populateCategories;
    }

    public boolean isPopulateTeams() {
        return populateTeams;
    }

    public void setPopulateTeams(boolean populateTeams) {
        this.populateTeams = populateTeams;
    }

    public int getShowDates() {
        return showDates;
    }

    public void setShowDates(int showDates) {
        this.showDates = showDates;
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    public long getUserIdVotedOn() {
        return userIdVotedOn;
    }

    public void setUserIdVotedOn(long userIdVotedOn) {
        this.userIdVotedOn = userIdVotedOn;
    }

    public long getUserIdThumbsUp() {
        return userIdThumbsUp;
    }

    public void setUserIdThumbsUp(long userIdThumbsUp) {
        this.userIdThumbsUp = userIdThumbsUp;
    }

    public long getUserIdThumbsDown() {
        return userIdThumbsDown;
    }

    public void setUserIdThumbsDown(long userIdThumbsDown) {
        this.userIdThumbsDown = userIdThumbsDown;
    }

    public boolean isCountComments() {
        return countComments;
    }

    public void setCountComments(boolean countComments) {
        this.countComments = countComments;
    }

}