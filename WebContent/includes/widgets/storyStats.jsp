<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="actionClass" value="com.breaker.webapp.includes.widgets.StoryStatsAction" scope="request"/>
<%@ include file="/includes/misc/mvc.jsp" %>


<table class="scores">
	<tr>
		<td class="vote_up_tally"><c:out value="${StoryStatsView.thumbsUp}"/></td>
		<td class="vote_down_tally"><c:out value="${StoryStatsView.thumbsDown}"/></td>
	</tr>

	<tr>
		<td class="stats">
			
			<a
				<c:if test="${StoryStatsView.loggedIn}">
					href="javascript:vote(${StoryStatsView.storyId}, 'up', 'story_listing_vote_${StoryStatsView.storyId}', true, ${StoryStatsView.loggedIn});"
				</c:if>
				<c:if test="${not StoryStatsView.loggedIn}">
					href="/login.jsp?msg=You may not cast a vote without first logging in."
				</c:if>
			>
				<c:if test="${StoryStatsView.userVote != 2}"><img src="/images/thumbs_up.jpg"></c:if>
				<c:if test="${StoryStatsView.userVote == 2}"><img src="/images/thumbs_up_pressed.jpg"></c:if>  
			</a>
		</td>
		<td class="stats">
			<a
				<c:if test="${StoryStatsView.loggedIn}">
					href="javascript:vote(${StoryStatsView.storyId}, 'down', 'story_listing_vote_${StoryStatsView.storyId}', true, ${StoryStatsView.loggedIn});"
				</c:if>
				<c:if test="${not StoryStatsView.loggedIn}">
					href="/login.jsp?msg=You may not cast a vote without first logging in."
				</c:if>
			>
				<c:if test="${StoryStatsView.userVote != 3}"><img src="/images/thumbs_down.jpg"></c:if>
				<c:if test="${StoryStatsView.userVote == 3}"><img src="/images/thumbs_down_pressed.jpg"></c:if>
			</a>
		</td>
	</tr>

	<tr>
		<td class="stats">VOTES</td>
		<td class="stats"><c:out value="${StoryStatsView.votes}"/></td>
	</tr>
	<tr class="grey_background">
		<td class="stats">VISITS</td>
		<td class="stats"><c:out value="${StoryStatsView.urlViews}"/></td>
	</tr>
	<tr>
		<td class="stats">C.A.R.</td>
		<td class="stats"><c:out value="${StoryStatsView.currentActivityRating}"/></td>
	</tr>
</table>
