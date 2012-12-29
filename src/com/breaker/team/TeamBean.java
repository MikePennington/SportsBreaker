package com.breaker.team;

import com.breaker.interfaces.XMLObject;
import com.breaker.newsstory.CategoryBean;
import com.breaker.newsstory.CategoryFactory;
import com.breaker.utils.XMLUtils;

public class TeamBean implements XMLObject {
    public static final String XML_TEAM          = "team";
    public static final String XML_ID            = "id";
    public static final String XML_NAME          = "name";
    public static final String XML_DISPLAY_NAME  = "display_name";
    public static final String XML_LOCATION      = "location";
    public static final String XML_CONFERENCE    = "conference";
    public static final String XML_SUBCONFERENCE = "subconference";
    public static final String XML_CATEGORY_ID   = "category_id";

    private int                teamId;
    private String             teamName;
    private String             location;
    private String             conference;
    private String             subconference;
    private int                categoryId;
    private boolean            college;
    private int                collegeId;

    public String toXMLString() {
        StringBuilder xml = new StringBuilder();
        xml.append(XMLUtils.openTag(XML_TEAM));
        xml.append(XMLUtils.openTag(XML_ID));
        xml.append(getTeamId());
        xml.append(XMLUtils.closeTag(XML_ID));
        xml.append(XMLUtils.openTag(XML_NAME));
        xml.append(XMLUtils.getXMLText(getTeamName()));
        xml.append(XMLUtils.closeTag(XML_NAME));
        xml.append(XMLUtils.openTag(XML_DISPLAY_NAME));
        xml.append(XMLUtils.getXMLText(getNoSportDisplayName()));
        xml.append(XMLUtils.closeTag(XML_DISPLAY_NAME));
        xml.append(XMLUtils.openTag(XML_LOCATION));
        xml.append(XMLUtils.getXMLText(getLocation()));
        xml.append(XMLUtils.closeTag(XML_LOCATION));
        xml.append(XMLUtils.openTag(XML_CONFERENCE));
        xml.append(XMLUtils.getXMLText(getConference()));
        xml.append(XMLUtils.closeTag(XML_CONFERENCE));
        xml.append(XMLUtils.openTag(XML_SUBCONFERENCE));
        xml.append(XMLUtils.getXMLText(getSubconference()));
        xml.append(XMLUtils.closeTag(XML_SUBCONFERENCE));
        xml.append(XMLUtils.openTag(XML_CATEGORY_ID));
        xml.append(getCategoryId());
        xml.append(XMLUtils.closeTag(XML_CATEGORY_ID));
        xml.append(XMLUtils.closeTag(XML_TEAM));
        return xml.toString();
    }

    public String getDisplayName() {
        StringBuilder sb = new StringBuilder();
        if (location != null && !college)
            sb.append(location);
        if (teamName != null) {
            if (sb.length() > 0)
                sb.append(" ");
            sb.append(teamName);
            if (college) {
                CategoryBean category = CategoryFactory.getCategoryById(categoryId);
                sb.append(" ");
                sb.append(category.getName());
            }
        }
        return sb.toString();
    }

    /**
     * The RSS feeds combine all the sports for the college feeds, so we don't
     * want to displaythe actual sport.
     * 
     * @return
     */
    public String getNoSportDisplayName() {
        StringBuilder sb = new StringBuilder();
        if (location != null && !college)
            sb.append(location);
        if (teamName != null) {
            if (sb.length() > 0)
                sb.append(" ");
            sb.append(teamName);
        }
        return sb.toString();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubconference() {
        return subconference;
    }

    public void setSubconference(String subconference) {
        this.subconference = subconference;
    }

    public boolean isCollege() {
        return college;
    }

    public void setCollege(boolean college) {
        this.college = college;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else {
            if (obj instanceof TeamBean) {
                TeamBean test = (TeamBean) obj;
                if (test.getTeamId() == this.getTeamId())
                    return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

}