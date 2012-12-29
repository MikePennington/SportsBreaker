<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="actionClass" value="com.breaker.webapp.includes.navigation.TeamTabAction" scope="request"/>
<%@ include file="/includes/misc/mvc.jsp" %>



<div class="teamTabs">
	<div class="teamTabsInner" style="height: ${TeamTabView.browserStyleHeight}">
		<c:forEach items="${TeamTabView.teamTabs}" var="teamTab">
			<span
				<c:if test="${teamTab.selected}">class="teamTab_selected"</c:if>
				<c:if test="${not teamTab.selected}">class="teamTab"</c:if>>
					<a href="index.jsp?teamId=<c:out value="${teamTab.teamId}" />"><c:out value="${teamTab.teamName}"/></a>
			</span>
		</c:forEach>

		<span class="teamTab">
			<a href="profile.jsp?tab=teams">Add a Team</a>
		</span>

		<c:if test="${empty TeamTabView.teamTabs}">
			<span class="teamTab">
				<a href="profile.jsp?tab=teams">Add a Team</a>
			</span>
			<span class="teamTab">
				<a href="profile.jsp?tab=teams">Add a Team</a>
			</span>
			<span class="teamTab">
				<a href="profile.jsp?tab=teams">Add a Team</a>
			</span>
		</c:if>

		<span style="padding-left: 10px; font-size: 12px; color: #333333;">
			<a href="profile.jsp?tab=teams" style="color: #333333;">
				<c:if test="${empty TeamTabView.teamTabs}">Click to set up your Team Tabs</c:if>
				<c:if test="${not empty TeamTabView.teamTabs}">Edit Team Tabs</c:if>
			</a>
		</span>
	</div>
</div>


