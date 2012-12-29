package com.breaker.newsstory;

public class StoryStatsBean {

    private int    score;
    private int    thumbsUp;
    private int    thumbsDown;
    private int    detailViews;
    private int    urlViews;
    private double currentActivityRating;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(int thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    public int getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public int getDetailViews() {
        return detailViews;
    }

    public void setDetailViews(int detailViews) {
        this.detailViews = detailViews;
    }

    public double getCurrentActivityRating() {
        return currentActivityRating;
    }

    public void setCurrentActivityRating(double currentActivityRating) {
        this.currentActivityRating = currentActivityRating;
    }

    public int getUrlViews() {
        return urlViews;
    }

    public void setUrlViews(int clicks) {
        this.urlViews = clicks;
    }
}