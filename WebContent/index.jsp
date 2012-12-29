<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>
<c:set var="actionClass" value="com.breaker.webapp.IndexAction" scope="request"/>
<%@ include file="/includes/misc/mvc.jsp" %>


<html>
<head>
<title>${IndexView.pageTitle}</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="/style/style.css">
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/prototype.js"></script>
<link rel="alternate" type="application/rss+xml" title="${IndexView.rssFeedTitle}" href="${IndexView.rssLink}" />
</head>
<body>

<div class="container">

<c:import url="/includes/navigation/header.jsp"/>

<c:import url="/includes/navigation/teamTab.jsp"/>

<div class="break"></div>


<table border="0" cellpadding="0" cellspacing="0" class="body">
	<tr>
		<td class="news_list">

			<div class="sort_options">

				<a href="<c:url value="${IndexView.baseUriForResorting}&sort=3&show=1"/>" 
					<c:if test="${IndexView.sortBy == 3 && IndexView.showDates == 1}"> class="selected_sort"</c:if>>Popularity</a>
				<c:if test="${IndexView.sortBy == 3 && IndexView.showDates == 1}"><a href="${IndexView.rssLink}"><img src="images/rss_icon.jpg" /></a></c:if>
				|
	
				<a href="<c:url value="${IndexView.baseUriForResorting}&sort=1&show=3"/>"
					<c:if test="${IndexView.sortBy == 1 && IndexView.showDates == 3}">class="selected_sort"</c:if>>Most Recent</a>
				<c:if test="${IndexView.sortBy == 1 && IndexView.showDates == 3}"><a href="${IndexView.rssLink}"><img src="images/rss_icon.jpg" /></a></c:if>
				| 

				<a href="<c:url value="${IndexView.baseUriForResorting}&sort=2&show=2"/>"
					<c:if test="${IndexView.sortBy == 2 && IndexView.showDates == 2}">class="selected_sort"</c:if>>Top Today</a>
				<c:if test="${IndexView.sortBy == 2 && IndexView.showDates == 2}"><a href="${IndexView.rssLink}"><img src="images/rss_icon.jpg" /></a></c:if>
				| 
	
				<a href="<c:url value="${IndexView.baseUriForResorting}&sort=2&show=3"/>" 
					<c:if test="${IndexView.sortBy == 2 && IndexView.showDates == 3}">class="selected_sort"</c:if>>Month</a>
				<c:if test="${IndexView.sortBy == 2 && IndexView.showDates == 3}"><a href="${IndexView.rssLink}"><img src="images/rss_icon.jpg" /></a></c:if>
				| 
	
				<a href="<c:url value="${IndexView.baseUriForResorting}&sort=2&show=4"/>" 
					<c:if test="${IndexView.sortBy == 2 && IndexView.showDates == 4}">class="selected_sort"</c:if>>Year</a>
				<c:if test="${IndexView.sortBy == 2 && IndexView.showDates == 4}"><a href="${IndexView.rssLink}"><img src="images/rss_icon.jpg" /></a></c:if>
				<img src="images/spacer.gif" height="1px" width="20px" />
				<a href="break.jsp"><img src="images/sb_break_story.jpg" /></a>
			</div>

			<hr class="dotted_across" />
			<div class="break"></div>

			<c:forEach items="${IndexView.stories}" var="story" varStatus="status">
				<c:set var="story" value="${story}" scope="request" />
				<c:import url="/includes/widgets/storyInList.jsp"/>
				<c:remove var="storyId" scope="request" />
				<c:if test="${!status.last}">
					<hr class="dotted_across">
				</c:if>
			</c:forEach>

			<table class="grey_banner">
				<tr>
					<td>
						<c:forEach items="${IndexView.navList}" var="navItem">
							<c:if test="${not empty navItem.url}">
								<a href="<c:out value="${navItem.url}" />"><c:out value="${navItem.text}" /></a>&nbsp;
							</c:if>
							<c:if test="${empty navItem.url}">
								<c:out value="${navItem.text}" />&nbsp;
							</c:if>
						</c:forEach>
					</td>
				</tr>
			</table>

		</td>

		<td class="right_column">
			
			<div class="adBoxHomeRight">
				<!-- Google adsense script -->
				<script type="text/javascript"><!--
					google_ad_client = "pub-6778488833626132";
					/* 300x250 */
					google_ad_slot = "4819844611";
					google_ad_width = 300;
					google_ad_height = 250;
					//-->
				</script>
				<script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
				</script>
			</div>
			<table class="national_rss">
				<tr class="national_rss_header">
					<td nowrap>
						<img src="/images/spacer.gif" height="1px" width="3px">
						<img src="/images/rss_icon.png" class="valign_middle">
						<img src="/images/spacer.gif" height="1px" width="3px">
						<span style="font-family: Georgia; font-size: 16px; vertical-align: middle;">${IndexView.rssTitle}</span>
					</td>
				</tr>
				<tr>
					<td>
						<div id='national_feeds'>
							<c:import url="/includes/widgets/nationalFeed.jsp"/>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<c:import url="/includes/navigation/footer.jsp"/>

</div>
</body>
</html>
