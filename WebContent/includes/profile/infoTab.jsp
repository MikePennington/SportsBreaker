<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>

<link rel="alternate" type="application/rss+xml" title="${ProfileView.rssFeedTitle}" href="${ProfileView.rssLink}" />

<table border="0" cellpadding="0" cellspacing="0" class="profile_body">
	<tr>
		<td class="profile_body_left">
			
			<div class="bigger bolder black"><c:out value="${ProfileView.user.userName}"/></div>
			<div class="break"></div>

			<c:if test="${not empty ProfileView.user.pictureFileUrl}">
				<img src="${ProfileView.user.pictureFileUrl}" />
				<div class="break"></div>
			</c:if>

			<div class="bold black">Member&nbsp;Since</div><c:out value="${ProfileView.benchingSince}"/>
			<div style="height: 10px;"></div>
			<span class="bold black">Stories&nbsp;Broken:&nbsp;</span><c:out value="${ProfileView.numStoriesBroken}"/>

			<c:if test="${not empty ProfileView.user.realName}">
				<div style="height: 10px;"></div>
				<span class="bold black">Real Name</span><br/><c:out value="${ProfileView.user.realName}"/>
			</c:if>

			<c:if test="${not empty ProfileView.user.hometown}">
				<div style="height: 10px;"></div>
				<span class="bold black">Hometown</span><br/><c:out value="${ProfileView.user.hometown}"/>
			</c:if>

			<c:if test="${not empty ProfileView.user.location}">
				<div style="height: 10px;"></div>
				<span class="bold black">Current Location</span><br/><c:out value="${ProfileView.user.location}"/>
			</c:if>

			<c:if test="${not empty ProfileView.user.college}">
				<div style="height: 10px;"></div>
				<span class="bold black">College</span><br/><c:out value="${ProfileView.user.college}"/>
			</c:if>

			<c:if test="${not empty ProfileView.user.highSchool}">
				<div style="height: 10px;"></div>
				<span class="bold black">High School</span><br/><c:out value="${ProfileView.user.highSchool}"/>
			</c:if>
			
			<c:if test="${not empty ProfileView.user.zipCode}">
				<div style="height: 10px;"></div>
				<span class="bold black">Zip Code</span><br/><c:out value="${ProfileView.user.zipCode}"/>
			</c:if>
			
			<c:if test="${not empty ProfileView.user.gender}">
				<div style="height: 10px;"></div>
				<span class="bold black">Gender</span><br/>
				<c:if test="${ProfileView.user.gender eq 'M'}">Male</c:if>
				<c:if test="${ProfileView.user.gender eq 'F'}">Female</c:if>
			</c:if>
			
			<c:if test="${not empty ProfileView.user.birthday}">
				<div style="height: 10px;"></div>
				<span class="bold black">Birthday</span><br/><c:out value="${ProfileView.user.birthdayAsStringNoYear}"/>
			</c:if>

		</td>
		<td class="profile_body_right">

			<div class="bigger bold">SportsBreaker History</div>

			<div>
				<a href="<c:url value="${ProfileView.baseUrl}&show=broken"/>" <c:if test="${ProfileView.showStoriesType eq 'broken'}">class="selected_sort big"</c:if>>Broken</a>
				<c:if test="${ProfileView.showStoriesType eq 'broken'}"><a href="${ProfileView.rssLink}"><img src="images/rss_icon.jpg" /></a></c:if> | 

				<a href="<c:url value="${ProfileView.baseUrl}&show=voted"/>"  <c:if test="${ProfileView.showStoriesType eq 'voted'}">class="selected_sort big"</c:if>>Voted On</a>
				<c:if test="${ProfileView.showStoriesType eq 'voted'}"><a href="${ProfileView.rssLink}"><img src="images/rss_icon.jpg" /></a></c:if> |

				<a href="<c:url value="${ProfileView.baseUrl}&show=thmup"/>"  <c:if test="${ProfileView.showStoriesType eq 'thmup'}">class="selected_sort big"</c:if>>Voted Thumbs Up</a>
				<c:if test="${ProfileView.showStoriesType eq 'thmup'}"><a href="${ProfileView.rssLink}"><img src="images/rss_icon.jpg" /></a></c:if> |

				<a href="<c:url value="${ProfileView.baseUrl}&show=thmdn"/>"  <c:if test="${ProfileView.showStoriesType eq 'thmdn'}">class="selected_sort big"</c:if>>Voted Thumbs Down</a>
				<c:if test="${ProfileView.showStoriesType eq 'thmdn'}"><a href="${ProfileView.rssLink}"><img src="images/rss_icon.jpg" /></a></c:if>
			</div>

			<hr class="across_table">
			<img src="/images/spacer.gif" height="10">

			<c:forEach items="${ProfileView.stories}" var="story" varStatus="status">

				<c:set var="story" value="${story}" scope="request" />
				<c:import url="/includes/widgets/storyInList.jsp"/>
				<c:remove var="storyId" scope="request" />

				<c:if test="${!status.last}">
					<hr class="dotted">
				</c:if>
			</c:forEach>

			<table class="grey_banner">
				<tr>
					<td>
						<c:forEach items="${ProfileView.navList}" var="navItem">
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
	</tr>
</table>