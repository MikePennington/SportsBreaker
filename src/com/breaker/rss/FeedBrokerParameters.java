package com.breaker.rss;

public class FeedBrokerParameters {
    public static final int  MAX_FEEDS_TO_RETURN   = 100;
    private static final int DEFAULT_NUM_FEEDS     = 10;

    private int              numberOfFeedsToReturn = DEFAULT_NUM_FEEDS;
    private int              startPosition;
    private int              userId;
    private boolean          returnBrokenById;
    private int              teamId;
    private int              collegeId;
    private int              categoryId;
    private long             rssId;

    public int getNumberOfFeedsToReturn() {
        return numberOfFeedsToReturn;
    }

    public void setNumberOfFeedsToReturn(int numberOfFeedsToReturn) {
        this.numberOfFeedsToReturn = numberOfFeedsToReturn;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isReturnBrokenById() {
        return returnBrokenById;
    }

    public void setReturnBrokenById(boolean returnBrokenById) {
        this.returnBrokenById = returnBrokenById;
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

    public long getRssId() {
        return rssId;
    }

    public void setRssId(long rssId) {
        this.rssId = rssId;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

}
