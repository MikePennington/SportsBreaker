package com.breaker.webapp.includes.navigation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.utils.RequestUtils;
import com.breaker.webapp.MVCActionInterface;
import com.breaker.webapp.MVCRedirector;

public class InfoPagesTabsAction implements MVCActionInterface {

    public MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response) {

        String page = RequestUtils.getPageName(request);

        request.setAttribute("pageName", page);

        return null;
    }

}
