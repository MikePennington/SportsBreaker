<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="actionClass" value="com.breaker.webapp.StoryAction" scope="request"/>
<%@ include file="/includes/misc/mvc.jsp" %>
<c:set var="story" value="${StoryView.story}" />
<c:set var="user" value="${StoryView.brokenBy}" />

<html>
<head>
<title>SportsBreaker - <c:out value="${story.title}"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="/style/style.css">
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/prototype.js"></script>
</head>
<body>

<div class="container">
	
	<c:import url="/includes/navigation/header.jsp"/>
	
	<c:import url="/includes/navigation/teamTab.jsp"/>
	
	<div class="body">

		<c:if test="${not empty StoryView.error}">
			<div class="error_text"><c:out value="${StoryView.error}"/></div>
		</c:if>

		<table width="95%">
			<tr>
				<td style="vertical-align: top;">
					<c:set var="story" value="${story}" scope="request" />
					<c:set var="hideMore" value="true" scope="request" />
					<c:import url="/includes/widgets/storyInList.jsp"/>
					<c:remove var="storyId" scope="request" />
				</td>
				<td width="205px" align="right">
					<div style="border: 1px solid #cdcdcd;">
					<script type="text/javascript"><!--
						google_ad_client = "pub-6778488833626132";
						/* 200x200, created 12/30/08 */
						google_ad_slot = "3840087979";
						google_ad_width = 200;
						google_ad_height = 200;
						//-->
					</script>
					<script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
					</script>
					</div>
				</td>
			</tr>
		</table>
	
	<br>
	
	<div class="comments_banner">
		Comments (<c:out value="${StoryView.numberOfComments}" />)
		<span style="font-size: 10px"><a href="<c:url value="story.jsp?id=${StoryView.story.storyId}&replyTo=0"/>">Post new comment</a></span>
	</div>
	

	<br>
	
	<div id="commentsContainer">
	<c:if test="${StoryView.newComment}">
		<table class="comment" cellspacing="0" cellpadding="0" style="width: 400px;">
			<th class="comment" align="left">Post a new comment</th>
			<tr>
				<td class="comment" colspan="2"><c:out value="${commentHelper.commentBean.comment}"/></td>
			</tr>
			<tr>
				<td class="comment" colspan="2">
					<form name="reply_form" method="post">
						<textarea name="newComment" rows="5" style="width: 98%;"></textarea><br>
						<input type="hidden" name="replying" value="true">
						<span style="float: right; margin-right: 5px;"><input type="submit" name="newCommentSubmit" value="Submit Comment"></span>							
					</form>
				</td>
			</tr>
		</table>
		<br>
	</c:if>


	<c:forEach items="${StoryView.comments}" var="commentHelper" varStatus="status">
		<a name="${commentHelper.commentBean.commentId}"></a>
		<table class="comment" cellspacing="0" cellpadding="0" style="margin-left: ${commentHelper.leftMargin}px; width: ${commentHelper.width}px">
			<tr>
				<th class="comment" align="left">
					Posted by 
					<a href="<c:url value="/profile.jsp?userId=${commentHelper.commentBean.user.userId}"/>">
						<b><c:out value="${commentHelper.commentBean.user.userName}"/></b>
					</a>
					on ${commentHelper.commentBean.formattedDate}
				</th>
				<th class="comment" align="right">
					<a href="<c:url value="story.jsp?id=${StoryView.story.storyId}&replyTo=${commentHelper.commentBean.commentId}#${commentHelper.commentBean.commentId}"/>">Reply</a>
				</th>
			</tr>
			<tr>
				<td class="comment" colspan="2"><c:out value="${commentHelper.commentBean.comment}"/></td>
			</tr>
			<c:if test="${StoryView.replyToId == commentHelper.commentBean.commentId}">
				<tr>
					<td class="comment" colspan="2">
						<form name="reply_form" method="post">
							<textarea name="replyComment" rows="5" style="width: 98%;"></textarea><br>
							<input type="hidden" name="replying" value="true">
							<span style="float: right; margin-right: 5px;"><input type="submit" name="replySubmit" value="Submit Reply"></span>							
						</form>
					</td>
				</tr>
			</c:if>
		</table>
		<br>
	</c:forEach>

	</div>


	<c:import url="/includes/navigation/footer.jsp"/>
</div>

</body>
</html>

