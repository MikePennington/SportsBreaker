<%@page import="com.breaker.user.LoginUtils"%>

<%
    if (!LoginUtils.isLoggedIn(request, response)) {
        response.sendRedirect("/beta/");
        return;
    } else {
        response.sendRedirect("/index.jsp");
        return;
    }
%>