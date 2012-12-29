package com.breaker.team;

import java.io.Serializable;

public class TeamTabBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private long              userId;
    private int               teamId;
    private int               sortOrder;
    private String            name;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))
            return true;
        if (obj == null)
            return false;
        if (obj instanceof TeamTabBean) {
            TeamTabBean test = (TeamTabBean) obj;
            if (this.getUserId() == test.getUserId() && this.getTeamId() == test.getTeamId())
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (this.getUserId() * this.getTeamId());
    }
}
