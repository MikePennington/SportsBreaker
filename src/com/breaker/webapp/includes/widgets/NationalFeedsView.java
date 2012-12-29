package com.breaker.webapp.includes.widgets;

import java.util.List;

import com.breaker.rss.FeedItemInfo;

public class NationalFeedsView {

    private boolean            showPrevious;
    private boolean            showNext;
    private int                previousIndex;
    private int                nextIndex;
    private List<FeedItemInfo> rssItems;
    private boolean            loggedIn;

    public List<FeedItemInfo> getRssItems() {
        return rssItems;
    }

    public void setRssItems(List<FeedItemInfo> rssItems) {
        this.rssItems = rssItems;
    }

    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public int getPreviousIndex() {
        return previousIndex;
    }

    public void setPreviousIndex(int previousIndex) {
        this.previousIndex = previousIndex;
    }

    public int getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(int nextIndex) {
        this.nextIndex = nextIndex;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}