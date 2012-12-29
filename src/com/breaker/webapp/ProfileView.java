package com.breaker.webapp;

import java.util.List;

import com.breaker.navigation.NavItemBean;
import com.breaker.newsstory.CategoryBean;
import com.breaker.newsstory.StoryBean;
import com.breaker.team.TeamBean;
import com.breaker.team.TeamTabBean;
import com.breaker.user.User;

public class ProfileView extends BasicView {

    /* For all tabs */
    private String             tab;
    private User               user;
    private String             baseUrl;
    private boolean            myProfile;

    /* For info tab */
    private List<StoryBean>    stories;
    private List<NavItemBean>  navList;
    private String             numStoriesBroken;
    private String             benchingSince;
    private String             showStoriesType;
    private String             rssLink;
    private String             rssFeedTitle;

    /* For manage teams tab */
    private List<TeamTabBean>  teamTabs;
    private List<CategoryBean> categories;
    private List<TeamBean>     teams;
    private int                categoryIdSelected;
    private int                teamIdSelected;
    private String             newTabName;
    private int                teamIdToRename;

    public List<StoryBean> getStories() {
        return stories;
    }

    public void setStories(List<StoryBean> stories) {
        this.stories = stories;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public List<NavItemBean> getNavList() {
        return navList;
    }

    public void setNavList(List<NavItemBean> navList) {
        this.navList = navList;
    }

    public List<CategoryBean> getCategories() {
        return categories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategories(List<CategoryBean> categories) {
        this.categories = categories;
    }

    public List<TeamTabBean> getTeamTabs() {
        return teamTabs;
    }

    public void setTeamTabs(List<TeamTabBean> teamTabs) {
        this.teamTabs = teamTabs;
    }

    public int getTeamIdToRename() {
        return teamIdToRename;
    }

    public void setTeamIdToRename(int teamIdToRename) {
        this.teamIdToRename = teamIdToRename;
    }

    public String getNumStoriesBroken() {
        return numStoriesBroken;
    }

    public void setNumStoriesBroken(String numStoriesBroken) {
        this.numStoriesBroken = numStoriesBroken;
    }

    public String getBenchingSince() {
        return benchingSince;
    }

    public void setBenchingSince(String benchingSince) {
        this.benchingSince = benchingSince;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getShowStoriesType() {
        return showStoriesType;
    }

    public void setShowStoriesType(String showStoriesType) {
        this.showStoriesType = showStoriesType;
    }

    public boolean isMyProfile() {
        return myProfile;
    }

    public void setMyProfile(boolean myProfile) {
        this.myProfile = myProfile;
    }

    public int getCategoryIdSelected() {
        return categoryIdSelected;
    }

    public void setCategoryIdSelected(int categoryIdSelected) {
        this.categoryIdSelected = categoryIdSelected;
    }

    public int getTeamIdSelected() {
        return teamIdSelected;
    }

    public void setTeamIdSelected(int teamIdSelected) {
        this.teamIdSelected = teamIdSelected;
    }

    public String getNewTabName() {
        return newTabName;
    }

    public void setNewTabName(String newTabName) {
        this.newTabName = newTabName;
    }

    public List<TeamBean> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamBean> teams) {
        this.teams = teams;
    }

    public String getRssLink() {
        return rssLink;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
    }

    public String getRssFeedTitle() {
        return rssFeedTitle;
    }

    public void setRssFeedTitle(String rssFeedTitle) {
        this.rssFeedTitle = rssFeedTitle;
    }

}
