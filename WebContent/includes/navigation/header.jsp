<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="actionClass" value="com.breaker.webapp.includes.navigation.HeaderAction" scope="request"/>
<%@ include file="/includes/misc/mvc.jsp" %>


<link rel="stylesheet" type="text/css" href="/style/menu.css">
<script type="text/javascript" src="/style/menu.js"></script>

<div style="background-color: #003D69;">
<table cellpadding="0" cellspacing="0" class="header">
	<tr>
		<td class="header_logo">
			<a href="<c:url value="index.jsp"/>"><img src="/images/sb_logo.jpg" border="0"></a>
		</td>
		<td class="header" align="left">
			<img src="/images/sb_header_graphic.png" usemap="#startTwitter">
			<map name="startTwitter">
			<area shape="rect" coords="0,0,175,48" href="<c:url value="start.jsp"/>">
			<area shape="rect" coords="175,0,300,48" href="http://www.twitter.com/sportsbreaker" target="_blank">
			</map>
			<img src="images/spacer.gif" height="1px" width="30px" />
		</td>
		<td class="header" nowrap>
			<div class="big_bold">Welcome <c:out value="${HeaderView.userName}"/>!</div>
			<c:if test="${HeaderView.loggedIn}">
				<a href="<c:url value="profile.jsp"/>">My Profile</a> | 
				<a href="<c:url value="${HeaderView.logoutUrl}"/>">Logout</a>
			</c:if>
			<c:if test="${!HeaderView.loggedIn}">
				<a href="<c:url value="login.jsp"/>">Login</a> | <a href="<c:url value="signup.jsp"/>">Sign Up</a> 
			</c:if>
		</td>
	</tr>
</table>
</div>

<div class="chromestyle" id="chromemenu">
	<div class="categories">
		<ul>
			<c:forEach items="${HeaderView.categories}" var="category" end="${HeaderView.categoriesToShow}">
				<c:choose>
					<c:when test="${category.id == HeaderView.selectedCategory}">
						<li><a href="<c:url value="index.jsp?catId=${category.id}"/>" class="category_selected"><c:out value="${category.name}"/></a></li>
					</c:when>
					<c:otherwise>
						<li><a href="<c:url value="index.jsp?catId=${category.id}"/>" class="category"><c:out value="${category.name}"/></a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${HeaderView.showMoreDropDown}">
				<li><a href="<c:url value="#"/>" class="category" rel="dropmenu1">More <img src="/images/arrow_down.gif" class="valign_middle"></a></li>
			</c:if>
		</ul>
	</div>
</div>

<!--1st drop down menu --> 
<div id="dropmenu1" class="dropmenudiv">
	<c:forEach items="${HeaderView.categories}" var="category" begin="${HeaderView.categoriesToShow + 1}">
		<a href="<c:url value="index.jsp?catId=${category.id}"/>"><c:out value="${category.name}"/></a>
	</c:forEach>
</div>
<script type="text/javascript">
cssdropdown.startchrome("chromemenu")
</script>

