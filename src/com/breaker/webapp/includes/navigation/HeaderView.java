package com.breaker.webapp.includes.navigation;

import java.util.List;

import com.breaker.newsstory.CategoryBean;

/**
 * View class for /includes/navigation/header.jsp
 * 
 * @author Mike Pennington
 */
public class HeaderView {

    private List<CategoryBean> categories;
    private boolean            loggedIn;
    private String             userName;
    private String             logoutUrl;
    private int                categoriesToShow;
    private int                selectedCategory;
    private int                selectedTeam;
    private boolean            showMoreDropDown;

    public List<CategoryBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryBean> categories) {
        this.categories = categories;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public int getCategoriesToShow() {
        return categoriesToShow;
    }

    public void setCategoriesToShow(int categoriesToShow) {
        this.categoriesToShow = categoriesToShow;
    }

    public int getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(int selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public int getSelectedTeam() {
        return selectedTeam;
    }

    public void setSelectedTeam(int selectedTeam) {
        this.selectedTeam = selectedTeam;
    }

    public boolean isShowMoreDropDown() {
        return showMoreDropDown;
    }

    public void setShowMoreDropDown(boolean showMoreDropDown) {
        this.showMoreDropDown = showMoreDropDown;
    }
}