package com.breaker.webapp.includes.navigation;

import java.util.List;

public class TeamTabView {

    private List<TeamTab> teamTabs;
    private String        browserStyleHeight;

    public String getBrowserStyleHeight() {
        return browserStyleHeight;
    }

    public void setBrowserStyleHeight(String browserStyleHeight) {
        this.browserStyleHeight = browserStyleHeight;
    }

    public List<TeamTab> getTeamTabs() {
        return teamTabs;
    }

    public void setTeamTabs(List<TeamTab> teamTabs) {
        this.teamTabs = teamTabs;
    }

    public class TeamTab {
        private int     teamId;
        private String  teamName;
        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public int getTeamId() {
            return teamId;
        }

        public void setTeamId(int teamId) {
            this.teamId = teamId;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }
    }

}
