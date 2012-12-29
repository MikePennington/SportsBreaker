package com.breaker.webapp.includes.widgets;

import com.breaker.webapp.BasicView;

public class StoryStatsView extends BasicView {

    private int     storyId;
    private String  score;
    private String  thumbsUp;
    private String  thumbsDown;
    private String  votes;
    private String  detailViews;
    private String  urlViews;
    private String  currentActivityRating;
    private int     userVote;
    private boolean loggedIn;

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(String thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    public String getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(String thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public String getCurrentActivityRating() {
        return currentActivityRating;
    }

    public void setCurrentActivityRating(String currentActivityRating) {
        this.currentActivityRating = currentActivityRating;
    }

    public int getUserVote() {
        return userVote;
    }

    public void setUserVote(int userVote) {
        this.userVote = userVote;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public String getDetailViews() {
        return detailViews;
    }

    public void setDetailViews(String detailViews) {
        this.detailViews = detailViews;
    }

    public String getUrlViews() {
        return urlViews;
    }

    public void setUrlViews(String urlViews) {
        this.urlViews = urlViews;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }
}