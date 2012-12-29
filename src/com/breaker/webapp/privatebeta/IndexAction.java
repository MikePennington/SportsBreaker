package com.breaker.webapp.privatebeta;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.webapp.MVCActionInterface;
import com.breaker.webapp.MVCRedirector;

public class IndexAction implements MVCActionInterface {

    public MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response) {
        MVCRedirector redirector = new MVCRedirector();
        redirector.setRedirectUrl("/index.jsp");
        return redirector;
    }
}
