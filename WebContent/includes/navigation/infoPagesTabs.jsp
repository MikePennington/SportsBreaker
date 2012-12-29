<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="actionClass" value="com.breaker.webapp.includes.navigation.InfoPagesTabsAction" scope="request"/>
<%@ include file="/includes/misc/mvc.jsp" %>

<div style="height: 1em;"></div>

<table cellpadding="0" cellspacing="0" class="tabs">
	<tr>
		<td class="tab_buffer"><img src="spacer.gif" width="50px" height="1px"></td>
		<td 
			<c:if test="${pageName eq 'about.jsp'}">class="tab_selected"</c:if> 
			<c:if test="${not (pageName eq 'about.jsp')}">class="tabs"</c:if> 
			nowrap><a href="about.jsp">About SportsBreaker</a></td>
		<td 
			<c:if test="${pageName eq 'start.jsp'}">class="tab_selected"</c:if> 
			<c:if test="${not (pageName eq 'start.jsp')}">class="tabs"</c:if> 
			nowrap><a href="start.jsp">Get Started</a></td>
		<td 
			<c:if test="${pageName eq 'buttons.jsp'}">class="tab_selected"</c:if> 
			<c:if test="${not (pageName eq 'buttons.jsp')}">class="tabs"</c:if> 
			nowrap><a href="buttons.jsp">Buttons</a></td>
		<td 
			<c:if test="${pageName eq 'tools.jsp'}">class="tab_selected"</c:if> 
			<c:if test="${not (pageName eq 'tools.jsp')}">class="tabs"</c:if> 
			nowrap><a href="tools.jsp">Tools</a></td>
		<td 
			<c:if test="${pageName eq 'terms.jsp'}">class="tab_selected"</c:if> 
			<c:if test="${not (pageName eq 'terms.jsp')}">class="tabs"</c:if> 
			nowrap><a href="terms.jsp">Terms of Use</a></td>
		<td 
			<c:if test="${pageName eq 'privacy.jsp'}">class="tab_selected"</c:if> 
			<c:if test="${not (pageName eq 'privacy.jsp')}">class="tabs"</c:if> 
			nowrap><a href="privacy.jsp">Privacy Policy</a></td>
		<td class="add_a_team"></td>
	</tr>
</table>