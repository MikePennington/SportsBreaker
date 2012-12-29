<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>
<c:set var="actionClass" value="com.breaker.webapp.includes.widgets.EmailStoryAction" scope="request"/>
<%@ include file="/includes/misc/mvc.jsp" %>

<div style="position: relative;">
	
	<c:if test="${not empty EmailStoryView.error}">
		<center><br/><div class="error_text"><c:out value="${EmailStoryView.error}" escapeXml="false"/></div></center>
	</c:if>

	<c:if test="${not EmailStoryView.emailed}">
	<table>
		<tr>
			<td><img src="images/email_story.jpg" /></td>
			<td align="right">
				<a href="javascript: hide('emailBox${EmailStoryView.storyId}'); grayOut(false,'');">
					<img src="images/close.jpg" style="float: right;" />
				</a>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div style="height: 10px;"></div>
				<img src="images/send_link_to.jpg" /><br/>
				<textarea id="emailTo${EmailStoryView.storyId}" style="height: 40px; width: 350px;">${EmailStoryView.to}</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div style="height: 10px;"></div>
				<img src="images/your_name.jpg" /><br/>
				<input id="emailFrom${EmailStoryView.storyId}" type="text" style="width: 350px;" value="${EmailStoryView.from}" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div style="height: 10px;"></div>
				<img src="images/message.jpg" /><br/>
				<textarea id="emailMsg${EmailStoryView.storyId}" style="height: 80px; width: 350px;">${EmailStoryView.msg}</textarea>
			</td>
		</tr>
		<tr>
			<td  colspan="2" align="right">
				<div style="height: 10px;"></div>
				<span class="big bold">
				<a href="javascript: emailStory('${EmailStoryView.storyId}', 'emailTo${EmailStoryView.storyId}', 'emailFrom${EmailStoryView.storyId}', 'emailMsg${EmailStoryView.storyId}');">Send Story!</a>
				</span>
			</td>
		</tr>
	</table>
	</c:if>

	<c:if test="${EmailStoryView.emailed}">
		Email send successfully!&nbsp;&nbsp;&nbsp;
		<a href="javascript: hide('emailBox${EmailStoryView.storyId}'); grayOut(false,'');">
			<img src="images/close.jpg" style="float: right;" />
		</a>
	</c:if>

</div>