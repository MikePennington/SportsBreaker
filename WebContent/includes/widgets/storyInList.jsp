<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<table>
	<tr>
		<td valign="top">
			<div id="story_stats_<c:out value="${story.storyId}"/>">
				<c:import url="/includes/widgets/storyStats.jsp"/>
			</div>
		</td>
		<td class="news_item_info" valign="top"> 
			<a href="/storyRedirect/?storyId=${story.storyId}" target="_blank" class="story_title">
				<c:out value="${story.title}"/>
			</a>

			<div class="story_desc" style="margin-top: 3px;">
				<c:out value="${story.description}" />
				<c:if test="${not hideMore}">
					<a href="<c:url value="story.jsp?id=${story.storyId}"/>">&nbsp;more&nbsp;&gt;&gt;</a>
				</c:if>
			</div>

			<div class="story_more_info">
				
				<div style="margin-top: 2px;">
					<c:if test="${not empty story.rssItem}">
						Source: 
						<c:choose>
							<c:when test="${not empty story.rssItem.channelWebpageLink}">
								<a href="<c:url value="${story.rssItem.channelWebpageLink}"/>" class="green">
								<c:out value="${story.rssItem.channelTitle}"/></a>
							</c:when>
							<c:otherwise>
								<i><c:out value="${story.rssItem.channelTitle}"/></i>
							</c:otherwise>
						</c:choose>
						 | 
					</c:if>
		
					<c:if test="${not empty story.categories}">
						Category: 
						<c:forEach items="${story.categories}" var="category" varStatus="status">
							<a href="index.jsp?catId=${category.id}" class="green"><c:out value="${category.name}" /></a>
							<c:if test="${!status.last}"> : </c:if>
						</c:forEach>
					</c:if>
				</div>
	
				<div style="margin-top: 2px;">
					Broken by <a href="<c:url value="/profile.jsp?userId=${story.brokenByUser.userId}"/>"><c:out value="${story.brokenByUser.userName}"/></a> <c:out value="${story.brokenDateStr}"/>
				</div>
				
				<c:if test="${not empty story.teams}">
					<div style="margin-top: 2px;">
						Team(s): 
						<span id="storyTeamsSome_${story.storyId}">
							<c:forEach items="${story.teams}" var="team" varStatus="status" end="6">
								<a href="index.jsp?teamId=<c:out value="${team.teamId}"/>"><c:out value="${team.displayName}" /></a>
								<c:if test="${!status.last}"> : </c:if>
							</c:forEach>
							<br/>
							<c:if test="${story.teamListSize gt 5}">
								<a href="javascript: show('storyTeamsAll_${story.storyId}'); hide('storyTeamsSome_${story.storyId}');">(show all teams)</a>
							</c:if>
						</span>
						<c:if test="${story.teamListSize gt 5}">
							<span id="storyTeamsAll_${story.storyId}" style="visibility: hidden; display: none;">
								<c:forEach items="${story.teams}" var="team" varStatus="status">
									<a href="index.jsp?teamId=<c:out value="${team.teamId}"/>"><c:out value="${team.displayName}" /></a>
									<c:if test="${!status.last}"> : </c:if>
								</c:forEach>
								<br/>
								<a href="javascript: hide('storyTeamsAll_${story.storyId}'); show('storyTeamsSome_${story.storyId}');">(show less teams)</a>
							</span>
						</c:if>
					</div>
				</c:if>

				<div style="height: 1em;"></div>
				<div>
					<a href="<c:url value="story.jsp?id=${story.storyId}"/>">
						<img src="images/comment_bubble.jpg" style="vertical-align: text-bottom;"> ${story.numberOfComments} Comments
					</a>
					&nbsp;&nbsp;
					<a href="javascript:show('emailBox${story.storyId}'); grayOut(true,''); absoluteCenter('emailBox${story.storyId}');">
						<img src="images/email.jpg" style="vertical-align: text-bottom;"> Email Story
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="popitup(buildFacebookShareUrl('${story.storyId}',encodeURIComponent('${story.titleForJavaScript}')));">
						<img src="images/sb_facebook_icon.jpg" style="vertical-align: text-bottom;"> Post to Facebook
					</a>

					<div id="emailBox${story.storyId}" class="emailBox">
						<c:import url="/includes/widgets/emailStory.jsp"/>
					</div>

					<!--<a href="#">Bookmark</a> | 
					<a href="#">Report a Problem</a>
					-->
				</div>
			</div>
		</td>
	</tr>
</table>

