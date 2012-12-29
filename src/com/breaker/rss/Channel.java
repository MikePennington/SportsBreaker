package com.breaker.rss;

import java.util.ArrayList;
import java.util.List;

public class Channel {

    private int        channelId;
    private String     link;
    private String     title;
    private String     description;
    private String     webpageLink;
    private String     language;
    private String     copyright;
    private int        active;
    private int        categoryId;
    private int        teamId;

    private Image      image;
    private List<Item> items;

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        if (items == null)
            items = new ArrayList<Item>();
        items.add(item);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
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

    public String getWebpageLink() {
        return webpageLink;
    }

    public void setWebpageLink(String webpageLink) {
        this.webpageLink = webpageLink;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}