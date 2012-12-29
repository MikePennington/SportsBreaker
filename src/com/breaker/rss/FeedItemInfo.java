package com.breaker.rss;

public class FeedItemInfo
{
    public static final int USER_STATUS_NOT_BROKEN  = 0;
    public static final int USER_STATUS_BROKEN      = 1;
    public static final int USER_STATUS_THUMBS_UP   = 2;
    public static final int USER_STATUS_THUMBS_DOWN = 3;

    private int             itemId;
    private String          title;
    private String          channelTitle;
    private String          description;
    private String          link;
    private String          pubDate;
    private int             channelId;
    private String          channelLink;
    private String          channelWebpageLink;
    private int             storyId;
    private int             categoryId;
    private long            brokenBy;
    private int             userStatus;

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public int getChannelId()
    {
        return channelId;
    }

    public void setChannelId(int channelId)
    {
        this.channelId = channelId;
    }

    public String getChannelLink()
    {
        return channelLink;
    }

    public void setChannelLink(String channelLink)
    {
        this.channelLink = channelLink;
    }

    public String getChannelTitle()
    {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle)
    {
        this.channelTitle = channelTitle;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getItemId()
    {
        return itemId;
    }

    public void setItemId(int itemId)
    {
        this.itemId = itemId;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getPubDate()
    {
        return pubDate;
    }

    public void setPubDate(String pubDate)
    {
        this.pubDate = pubDate;
    }

    public int getStoryId()
    {
        return storyId;
    }

    public void setStoryId(int storyId)
    {
        this.storyId = storyId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getUserStatus()
    {
        return userStatus;
    }

    public void setUserStatus(int userStatus)
    {
        this.userStatus = userStatus;
    }

    public long getBrokenBy()
    {
        return brokenBy;
    }

    public void setBrokenBy(long brokenBy)
    {
        this.brokenBy = brokenBy;
    }

    public String getChannelWebpageLink()
    {
        return channelWebpageLink;
    }

    public void setChannelWebpageLink(String channelWebpageLink)
    {
        this.channelWebpageLink = channelWebpageLink;
    }
}
