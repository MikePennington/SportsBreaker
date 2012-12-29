package com.breaker.newsstory;

import java.util.Date;

public class NewsStoryStat {

    private int  storyId;
    private int  statTypeId;
    private int  value;
    private long userId;
    private Date actionDate;

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public int getStatTypeId() {
        return statTypeId;
    }

    public void setStatTypeId(int statTypeId) {
        this.statTypeId = statTypeId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

}
