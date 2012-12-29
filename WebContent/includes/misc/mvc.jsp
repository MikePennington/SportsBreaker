<%@page import="com.breaker.utils.StringUtils"%>
<%@page import="com.breaker.webapp.MVCActionInterface"%>
<%@page import="com.breaker.webapp.MVCRedirector"%>
<%@page import="java.lang.Class"%>

<%
	try
	{
		String actionClassName = (String) request.getAttribute("actionClass");
		if(!StringUtils.isEmpty(actionClassName))
		{
		    Class<MVCActionInterface> actionClass = (Class<MVCActionInterface>) Class.forName(actionClassName);
		    MVCActionInterface mvcInterface = (MVCActionInterface) actionClass.newInstance();
		    MVCRedirector redirector = mvcInterface.doAction(request, response);
		    if(redirector != null && !StringUtils.isEmpty(redirector.getRedirectUrl()))
		    {
		        response.sendRedirect(redirector.getRedirectUrl());
		        return;
		    }
		}
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
	request.removeAttribute("actionClass");
%>