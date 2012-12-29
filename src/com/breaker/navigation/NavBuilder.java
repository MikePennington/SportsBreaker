package com.breaker.navigation;

import java.util.ArrayList;
import java.util.List;

import com.breaker.utils.StringUtils;

/**
 * This is a utility class for building out the navigation for browsing through
 * lists of items.
 * 
 * @author michael
 * 
 */
public class NavBuilder {
    private static final String START_PARAMETER  = "start";

    // These are "quasi-final" members. They should pretty much always be the
    // same, but are not set as static final because we might want to be able to
    // change them sometime.
    private int                 pagesAtOnce      = 10;
    private int                 itemsPerPage     = 10;

    private int                 currentPosition  = 0;
    private int                 totalNumElements = 0;
    private String              baseURL          = "";

    public List<NavItemBean> buidNavList() {
        List<NavItemBean> navList = new ArrayList<NavItemBean>();

        if (currentPosition > 0)
            navList.add(new NavItemBean(buildURL(currentPosition - itemsPerPage), "Previous"));
        else
            navList.add(new NavItemBean(null, "Previous"));

        navList.add(new NavItemBean(null, "|"));

        int startPage = 1;
        int maxPages = (int) Math.ceil((double) totalNumElements / (double) itemsPerPage);
        int endPage = maxPages;
        if (maxPages > pagesAtOnce && currentPosition > 0) {
            int currentPage = (int) Math.ceil(currentPosition / itemsPerPage) + 1;
            startPage = currentPage - (itemsPerPage / 2);
            if (startPage < 1)
                startPage = 1;
        }

        if (startPage + pagesAtOnce < endPage)
            endPage = startPage + pagesAtOnce;

        for (int page = startPage; page <= endPage; page++) {
            if (page == (currentPosition + itemsPerPage) / itemsPerPage)
                navList.add(new NavItemBean(null, String.valueOf(page)));
            else
                navList.add(new NavItemBean(buildURL((page - 1) * itemsPerPage), String.valueOf(page)));
        }

        navList.add(new NavItemBean(null, "|"));

        if (totalNumElements > currentPosition + itemsPerPage)
            navList.add(new NavItemBean(buildURL(currentPosition + itemsPerPage), "Next"));
        else
            navList.add(new NavItemBean(null, "Next"));

        return navList;
    }

    /**
     * A handy method for building out the URL for a NavItem.
     * 
     * @param start
     * @return
     */
    private String buildURL(int start) {
        if (start < 0)
            start = 0;
        StringBuilder sb = new StringBuilder(baseURL);
        StringUtils.appendParameter(sb, START_PARAMETER, start);
        return sb.toString();
    }

    /** ********** Only getters and setters are below this line *********** */
    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public int getTotalNumElements() {
        return totalNumElements;
    }

    public void setTotalNumElements(int totalNumElements) {
        this.totalNumElements = totalNumElements;
    }

}
