<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>
<c:set var="actionClass" value="com.breaker.webapp.ProfileAction" scope="request"/>
<%@ include file="/includes/misc/mvc.jsp" %>


<html>
<head>
<title>SportsBreaker - My Profile</title>

<!-- Turn caching off on this page -->
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="expires" content="0">

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="/style/style.css">
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/prototype.js"></script>
</head>
<body>

<div class="container">

	<c:import url="/includes/navigation/header.jsp"/>

	<div style="height: 1em;"></div>

	<c:if test="${ProfileView.myProfile}">
		<table cellpadding="0" cellspacing="0" class="tabs">
			<tr>
				<td class="tab_buffer"><img src="spacer.gif" width="50px" height="1px"></td>
				<td 
					<c:if test="${ProfileView.tab eq 'info'}">class="tab_selected"</c:if> 
					<c:if test="${not (ProfileView.tab eq 'info')}">class="tabs"</c:if> 
					nowrap><a href="profile.jsp?tab=info">Info</a>
				</td>
				<td 
					<c:if test="${ProfileView.tab eq 'teams'}">class="tab_selected"</c:if> 
					<c:if test="${not (ProfileView.tab eq 'teams')}">class="tabs"</c:if> 
					nowrap><a href="profile.jsp?tab=teams">Manage Team Tabs</a>
				</td>
				<td 
					<c:if test="${ProfileView.tab eq 'edit'}">class="tab_selected"</c:if> 
					<c:if test="${not (ProfileView.tab eq 'edit')}">class="tabs"</c:if> 
					nowrap><a href="profile.jsp?tab=edit">Edit Profile Information</a>
				</td>
				<td class="add_a_team"></td>
			</tr>
		</table>
	</c:if>

	<c:if test="${not empty ProfileView.error}">
		<div class="error_text">${ProfileView.error}</div>
	</c:if>

	<div class="break"></div>

	<c:choose>
		<c:when test="${ProfileView.tab eq 'teams'}">
			<c:import url="/includes/profile/manageTeamsTab.jsp"/>
		</c:when>
		<c:when test="${ProfileView.tab eq 'edit'}">
			<c:import url="/includes/profile/edit.jsp"/>
		</c:when>
		<c:otherwise>
			<jsp:include page="/includes/profile/infoTab.jsp" />
		</c:otherwise>
	</c:choose>
	
	<c:import url="/includes/navigation/footer.jsp"/>

</div>
</body>
</html>