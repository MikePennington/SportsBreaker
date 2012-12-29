package com.breaker.rss;

import java.util.Date;

/**
 * This class defines an RSS item that comes directly from a feed. See FeedItemInfo for a bean that contains info on an
 * RSS Item coming from out of the database.
 * 
 * @author Mike Pennington
 */
public class Item
{

    private int    itemId;
    private String guid;
    private String title;
    private String link;
    private String description;
    private Date   lastUpdated;
    private Date   publishedDate;
    private int    storyId;

    private String media_content;
    private String media_credit;
    private String media_text;
    private int    channelId;
    private String channelTitle;
    private String channelLink;
    private String dateAsString;

    private int    categoryId;

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getGuid()
    {
        return guid;
    }

    public void setGuid(String guid)
    {
        this.guid = guid;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public Date getPublishedDate()
    {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate)
    {
        this.publishedDate = publishedDate;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getMedia_content()
    {
        return media_content;
    }

    public void setMedia_content(String media_content)
    {
        this.media_content = media_content;
    }

    public String getMedia_credit()
    {
        return media_credit;
    }

    public void setMedia_credit(String media_credit)
    {
        this.media_credit = media_credit;
    }

    public String getMedia_text()
    {
        return media_text;
    }

    public void setMedia_text(String media_text)
    {
        this.media_text = media_text;
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

    public int getItemId()
    {
        return itemId;
    }

    public void setItemId(int itemId)
    {
        this.itemId = itemId;
    }

    public String getDateAsString()
    {
        return dateAsString;
    }

    public void setDateAsString(String dateAsString)
    {
        this.dateAsString = dateAsString;
    }

    public int getStoryId()
    {
        return storyId;
    }

    public void setStoryId(int storyId)
    {
        this.storyId = storyId;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int category)
    {
        categoryId = category;
    }

    public Date getLastUpdated()
    {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }
}