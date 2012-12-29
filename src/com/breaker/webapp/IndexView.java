package com.breaker.webapp;

import java.util.List;

import com.breaker.navigation.NavItemBean;
import com.breaker.newsstory.StoryBean;

public class IndexView {

    private int               startPosit;
    private int               endPosit;
    private String            baseUriForResorting;
    private List<StoryBean>   stories;
    private List<NavItemBean> navList;
    private int               sortBy;
    private int               showDates;
    private int               categoryId;
    private int               teamId;
    private String            rssTitle;
    private boolean           theWire;
    private String            pageTitle;
    private String            rssLink;
    private String            rssFeedTitle;

    public String getRssTitle() {
        return rssTitle;
    }

    public void setRssTitle(String rssTitle) {
        this.rssTitle = rssTitle;
    }

    public List<StoryBean> getStories() {
        return stories;
    }

    public void setStories(List<StoryBean> stories) {
        this.stories = stories;
    }

    public int getEndPosit() {
        return endPosit;
    }

    public void setEndPosit(int endPosit) {
        this.endPosit = endPosit;
    }

    public int getStartPosit() {
        return startPosit;
    }

    public void setStartPosit(int startPosit) {
        this.startPosit = startPosit;
    }

    public List<NavItemBean> getNavList() {
        return navList;
    }

    public void setNavList(List<NavItemBean> navList) {
        this.navList = navList;
    }

    public String getBaseUriForResorting() {
        return baseUriForResorting;
    }

    public void setBaseUriForResorting(String baseUriForResorting) {
        this.baseUriForResorting = baseUriForResorting;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public boolean isTheWire() {
        return theWire;
    }

    public void setTheWire(boolean theWire) {
        this.theWire = theWire;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
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