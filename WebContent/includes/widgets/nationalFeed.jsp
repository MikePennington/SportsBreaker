<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="actionClass" value="com.breaker.webapp.includes.widgets.NationalFeedAction" scope="request"/>
<%@ include file="/includes/misc/mvc.jsp" %>


<table width="100%" class="national_rss_navigation">
	<tr>
		<c:if test="${NationalFeedsView.showPrevious}">
			<td align="left">
				<a href="javascript:refreshRSSFeed(<c:out value="${NationalFeedsView.previousIndex}"/>)">
					&#x25C4; Previous
				</a>
			</td>
		</c:if>
		<c:if test="${!NationalFeedsView.showPrevious}">
			<td align="left">
				&#x25C4; Previous
			</td>
		</c:if>
		<c:if test="${NationalFeedsView.showNext}">
			<td align="right">
				<a href="javascript:refreshRSSFeed(<c:out value="${NationalFeedsView.nextIndex}"/>)">
					Next &#x25BA;
				</a>
			</td>
		</c:if>
		<c:if test="${!NationalFeedsView.showNext}">
			<td align="right">
				Next &#x25BA;
			</td>
		</c:if>
	</tr>
</table>
<hr style="across_table">

<c:forEach items="${NationalFeedsView.rssItems}" var="rssItem" varStatus="status">

	<div>
		<table>
			<tr>
				<td nowrap>
					<div class="national_rss_item_image">

						<c:if test="${rssItem.userStatus == 0}">
							<a href="break.jsp?rssId=<c:url value="${rssItem.itemId}"/>"><img src="/images/break.jpg" class="valign_tbottom"></a>
						</c:if>
						<c:if test="${rssItem.userStatus == 1 or rssItem.userStatus == 3}">
							<a 
								<c:if test="${NationalFeedsView.loggedIn}">
									href="javascript:voteFromRSSFeed(<c:out value="${rssItem.storyId}" />, 'up');"
								</c:if>
								<c:if test="${not NationalFeedsView.loggedIn}">
									href="/login.jsp?msg=You may not cast a vote without first logging in."
								</c:if>
							>
								<img src="/images/thumbs_up.jpg" class="valign_tbottom">
							</a>
						</c:if>
						<c:if test="${rssItem.userStatus == 2}">
							<img src="/images/thumbs_up_pressed.jpg" class="valign_tbottom">
						</c:if>
						<div style="height: 3px;"></div>
						<c:if test="${rssItem.userStatus == 1 or rssItem.userStatus == 2}">
							<a 
								<c:if test="${NationalFeedsView.loggedIn}">
									href="javascript:voteFromRSSFeed(<c:out value="${rssItem.storyId}" />, 'down');"
								</c:if>
								<c:if test="${not NationalFeedsView.loggedIn}">
									href="/login.jsp?msg=You may not cast a vote without first logging in."
								</c:if>
							>
								<img src="/images/thumbs_down.jpg" class="valign_tbottom">
							</a>
						</c:if>
						<c:if test="${rssItem.userStatus == 3}">
							<img src="/images/thumbs_down_pressed.jpg" class="valign_tbottom">
						</c:if>

					</div>
				</td>
				<td>
					<span class="dark_grey_headline">
						<strong><a href="<c:url value="${rssItem.link}" />" target="_blank"><c:out value="${rssItem.title}" escapeXml="false" /></a></strong>
					</span>
					<span id="rssItemMoreLink${rssItem.itemId}" style="margin-top: 2px;">
						<a href="javascript:showMoreRss('${rssItem.itemId}');">(more)</a>
					</span>
					<span id="rssItemLessLink${rssItem.itemId}" style="margin-top: 2px; visibility: hidden; display: none;">
						<a href="javascript:showLessRss('${rssItem.itemId}');">(less)</a>
					</span>
					<div class="small_italics">
						<c:out value="${rssItem.channelTitle}" escapeXml="false" /><br>
						Updated <c:out value="${rssItem.pubDate}" />
					</div>
				</td>
			</tr>
		</table>
	</div>

	<!-- Description -->
	<div style="margin-left: 55px; width: 230px; overflow: auto;">
	<div id="rssItemDesc${rssItem.itemId}" style="margin-top: 2px; visibility: hidden; display: none;">
		<c:out value="${rssItem.description}" escapeXml="false" />
	</div>
	</div>


	<c:if test="${!status.last}">
		<hr class="dotted">
	</c:if>

</c:forEach>

<hr style="across_table">
<table width="100%" class="national_rss_navigation">
	<tr>
		<c:if test="${NationalFeedsView.showPrevious}">
			<td align="left">
				<a href="javascript:refreshRSSFeed(<c:out value="${NationalFeedsView.previousIndex}"/>)">
					&#x25C4; Previous
				</a>
			</td>
		</c:if>
		<c:if test="${!NationalFeedsView.showPrevious}">
			<td align="left">
				&#x25C4; Previous
			</td>
		</c:if>
		<c:if test="${NationalFeedsView.showNext}">
			<td align="right">
				<a href="javascript:refreshRSSFeed(<c:out value="${NationalFeedsView.nextIndex}"/>)">
					Next &#x25BA;
				</a>
			</td>
		</c:if>
		<c:if test="${!NationalFeedsView.showNext}">
			<td align="right">
				Next &#x25BA;
			</td>
		</c:if>
	</tr>
</table>

<script type="text/javascript"><!--
	
	function showMoreRss(rssItemId) {
		show('rssItemDesc'+rssItemId);
		hide('rssItemMoreLink'+rssItemId);
		show('rssItemLessLink'+rssItemId);
	}
	
	function showLessRss(rssItemId) {
		hide('rssItemDesc'+rssItemId);
		show('rssItemMoreLink'+rssItemId);
		hide('rssItemLessLink'+rssItemId);
	}

//-->
</script>
