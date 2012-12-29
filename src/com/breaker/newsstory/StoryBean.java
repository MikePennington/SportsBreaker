package com.breaker.newsstory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.breaker.rss.FeedItemInfo;
import com.breaker.team.TeamBean;
import com.breaker.user.User;
import com.breaker.utils.StringUtils;

public class StoryBean {

    private int                storyId;
    private String             title;
    private String             description;
    private Date               brokenDate;
    private User               brokenByUser;
    private String             url;

    private int                numberOfComments;
    private StoryStatsBean     stats;
    private List<TeamBean>     teams;
    private List<CategoryBean> categories;
    private long               rssId;
    private FeedItemInfo       rssItem;

    /**
     * Convenience method for formating the broken date in the default format.
     * 
     * @return
     */
    public String getBrokenDateStr() {
        return StringUtils.formatDate(getBrokenDate());
    }

    public int getTeamListSize() {
        if (teams != null)
            return teams.size();
        else
            return 0;
    }

    public long getRssId() {
        return rssId;
    }

    public void setRssId(long rssId) {
        this.rssId = rssId;
    }

    public Date getBrokenDate() {
        return brokenDate;
    }

    public void setBrokenDate(Date brokenDate) {
        this.brokenDate = brokenDate;
    }

    public List<CategoryBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryBean> categories) {
        this.categories = categories;
    }

    public void addCategory(CategoryBean category) {
        if (categories == null)
            categories = new ArrayList<CategoryBean>();
        categories.add(category);
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public StoryStatsBean getStats() {
        if (stats == null)
            stats = StoryStatsFactory.getStatsBean(this);
        return stats;
    }

    public void setStats(StoryStatsBean stats) {
        this.stats = stats;
    }

    public List<TeamBean> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamBean> teams) {
        this.teams = teams;
    }

    public void addteam(TeamBean team) {
        if (teams == null)
            teams = new ArrayList<TeamBean>();
        teams.add(team);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleForJavaScript() {
        return title.replaceAll("'", "");
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FeedItemInfo getRssItem() {
        return rssItem;
    }

    public void setRssItem(FeedItemInfo rssItem) {
        this.rssItem = rssItem;
    }

    public User getBrokenByUser() {
        return brokenByUser;
    }

    public void setBrokenByUser(User brokenByUser) {
        this.brokenByUser = brokenByUser;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}