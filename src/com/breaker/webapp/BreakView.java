package com.breaker.webapp;

import java.util.List;

import com.breaker.newsstory.CategoryBean;
import com.breaker.team.TeamBean;

/**
 * View class for break.jsp
 * 
 * @author michael
 * 
 */
public class BreakView {

    private String             url;
    private String             title;
    private String             description;
    private String             error;
    private List<CategoryBean> categories;
    private int                defaultCategoryId;
    private int                selectedCategoryId;
    private String             selectedTeamIds;
    private List<TeamBean>     selectedTeams;

    public int getDefaultCategoryId() {
        return defaultCategoryId;
    }

    public void setDefaultCategoryId(int defaultCategoryId) {
        this.defaultCategoryId = defaultCategoryId;
        this.selectedCategoryId = defaultCategoryId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<CategoryBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryBean> categories) {
        this.categories = categories;
    }

    public String getSelectedTeamIds() {
        return selectedTeamIds;
    }

    public void setSelectedTeamIds(String selectedTeamIds) {
        this.selectedTeamIds = selectedTeamIds;
    }

    public List<TeamBean> getSelectedTeams() {
        return selectedTeams;
    }

    public void setSelectedTeams(List<TeamBean> selectedTeams) {
        this.selectedTeams = selectedTeams;
    }

    public int getSelectedCategoryId() {
        return selectedCategoryId;
    }

    public void setSelectedCategoryId(int selectedCategoryId) {
        this.selectedCategoryId = selectedCategoryId;
    }
}