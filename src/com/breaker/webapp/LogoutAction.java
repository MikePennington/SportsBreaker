package com.breaker.webapp;

import com.breaker.user.LoginUtils;
import com.breaker.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action class for logout.jsp
 * 
 * @author michael
 */
public class LogoutAction implements MVCActionInterface
{

    public MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response)
    {
        LoginUtils.logout(request, response);
        String caller = request.getParameter("caller");
        if (!StringUtils.isEmpty(caller))
        {
            MVCRedirector redirector = new MVCRedirector();
            if (!StringUtils.isEmpty(caller))
                redirector.setRedirectUrl(caller);
            else
                redirector.setRedirectUrl("/index.jsp");
            return redirector;
        }
        else
        {
            return null;
        }
    }
}