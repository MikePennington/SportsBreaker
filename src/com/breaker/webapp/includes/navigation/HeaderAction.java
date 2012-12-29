package com.breaker.webapp.includes.navigation;

import java.util.List;

import com.breaker.newsstory.CategoryBean;
import com.breaker.newsstory.CategoryFactory;
import com.breaker.user.LoginUtils;
import com.breaker.user.User;
import com.breaker.utils.RequestUtils;
import com.breaker.utils.StringUtils;
import com.breaker.webapp.MVCActionInterface;
import com.breaker.webapp.MVCRedirector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action class for /includes/navigation/header.jsp
 * 
 * @author michael
 * 
 */
public class HeaderAction implements MVCActionInterface {

    private static final int    CATEGORIES_TO_SHOW = 14;
    private static final String PARAMETER_TEAM     = "teamId";
    private static final String PARAMETER_CATEGORY = "catId";

    public MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response) {
        if ("true".equals(request.getParameter("logout"))) {
            MVCRedirector redirector = new MVCRedirector();
            redirector.setRedirectUrl("logout.jsp");
            return redirector;
        }
        CategoryFactory categoryFactory = new CategoryFactory();
        List<CategoryBean> categories = categoryFactory.getDisplayCategories(CategoryFactory.ORDER_BY_SORT_ORDER);
        boolean isLoggedIn = LoginUtils.isLoggedIn(request, response);
        String url = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        String logoutUrl = "logout.jsp?caller=";
        logoutUrl = logoutUrl + url;
        if (!StringUtils.isEmpty(queryString))
            logoutUrl = logoutUrl + "?" + queryString;
        String userName = "Guest";
        User user = LoginUtils.getUserObject(request, response);
        if (user != null)
            userName = user.getUserName();
        HeaderView view = new HeaderView();
        view.setCategories(categories);
        view.setLoggedIn(isLoggedIn);
        view.setUserName(userName);
        view.setLogoutUrl(logoutUrl);
        view.setCategoriesToShow(CATEGORIES_TO_SHOW);
        int teamId = RequestUtils.getParameterAsInt(request, PARAMETER_TEAM, 0);
        view.setSelectedTeam(teamId);
        view.setShowMoreDropDown(categories.size() > CATEGORIES_TO_SHOW + 1);

        int selectedCategory = RequestUtils.getParameterAsInt(request, PARAMETER_CATEGORY, 0);
        if (selectedCategory == 0 && "index.jsp".equals(RequestUtils.getPageName(request)) && teamId == 0)
            selectedCategory = 1;
        view.setSelectedCategory(selectedCategory);

        request.setAttribute("HeaderView", view);
        return null;
    }
}