<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>


<!-- For jscalendar -->
<link rel="stylesheet" type="text/css" media="all" href="/js/jscalendar/calendar-blue2.css" />
<script type="text/javascript" src="/js/jscalendar/calendar.js"></script>
<script type="text/javascript" src="/js/jscalendar/lang/calendar-en.js"></script>
<script type="text/javascript" src="/js/jscalendar/calendar-setup.js"></script>


<c:if test="${not empty ProfileView.message}">
	<div class="message_text"><c:out value="${ProfileView.message}"/></div>
</c:if>


<table>
	<tr>
		<td>
			<form name="userInfoForm" method="post">
			<table style="margin-left: 2em;">
				<tr>
					<td align="center" colspan="2" class="big bold">Personal Information</td>
				</tr>
				<tr>
					<td align="right">Username</td>
					<td><input type="text" name="username" value="${ProfileView.user.userName}" style="margin-left: 10px; width: 250px;"></td>
				</tr>
				<tr>
					<td align="right">Email</td>
					<td><input type="text" name="email" value="${ProfileView.user.email}" style="margin-left: 10px; width: 250px;"></td>
				</tr>
				<tr>
					<td align="right">Real Name</td>
					<td><input type="text" name="real_name" maxlength="255" value="${ProfileView.user.realName}" style="margin-left: 10px; width: 250px;"></td>
				</tr>
				<tr>
					<td align="right">Hometown</td>
					<td><input type="text" name="hometown" maxlength="255" value="${ProfileView.user.hometown}" style="margin-left: 10px; width: 250px;"></td>
				</tr>
				<tr>
					<td align="right">Current Location</td>
					<td><input type="text" name="location" maxlength="255" value="${ProfileView.user.location}" style="margin-left: 10px; width: 250px;"></td>
				</tr>
				<tr>
					<td align="right">College</td>
					<td><input type="text" name="college" maxlength="255" value="${ProfileView.user.college}" style="margin-left: 10px; width: 250px;"></td>
				</tr>
				<tr>
					<td align="right">High School</td>
					<td><input type="text" name="highSchool" maxlength="255" value="${ProfileView.user.highSchool}" style="margin-left: 10px; width: 250px;"></td>
				</tr>
				<tr>
					<td align="right">Gender</td>
					<td>
						<select name="gender" style="margin-left: 10px; width: 250px;">
							<option> - Please Select - </option>
							<option value="M" <c:if test="${ProfileView.user.gender eq 'M'}">selected</c:if>>Male</option>
							<option value="F" <c:if test="${ProfileView.user.gender eq 'F'}">selected</c:if>>Female</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">Birthday</td>
					<td>
						<input type="text" name="birthday" id="birthday" value="${ProfileView.user.birthdayAsString}" style="margin-left: 10px; width: 250px" />
						<button type="reset" id="birthday_button">Select Date</button>
						<script type="text/javascript">
						    Calendar.setup({
						        inputField     :    "birthday",      // id of the input field
						        ifFormat       :    "%m/%d/%Y",       // format of the input field
						        showsTime      :    false,            // will display a time selector
						        button         :    "birthday_button",   // trigger for the calendar (button ID)
						        singleClick    :    true,           // double-click mode
						        step           :    1                // show all years in drop-down boxes (instead of every other year as default)
						    });
						</script>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="hidden" name="userInfoFormSubmit" value="true"/>
						<input type="submit" name="submit" value="Update Personal Information"/>
					</td>
				</tr>
			</table>
			</form>
		</td>
		<td valign="top">
			<form name="changePasswordForm" method="post">
			<table style="margin-left: 2em;">
				<tr>
					<td align="center" colspan="2" class="big bold">Change Password</td>
				</tr>
				<tr>
					<td align="right">Old Password</td>
					<td><input type="password" name="old_password" size="30" value="" style="margin-left: 10px;"></td>
				</tr>
				<tr>
					<td align="right">New Password</td>
					<td><input type="password" name="new_password1" size="30" value="" style="margin-left: 10px;"></td>
				</tr>
				<tr>
					<td align="right">Retype New Password</td>
					<td><input type="password" name="new_password2" size="30" maxlength="255" value="" style="margin-left: 10px;"></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="hidden" name="changePasswordFormSubmit" value="true" />
						<input type="submit" name="submit" value="Change Password" />
					</td>
				</tr>
			</table>
			</form>
		</td>
	</tr>
</table>





<div class="break"></div>
<hr class="dotted" />
<div style="padding-left: 5em;">
	<div class="big bold">Profile Picture</div>
	<div class="break"></div>
	<form method="post" enctype="multipart/form-data">
		<c:if test="${not empty ProfileView.user.pictureFileUrl}">
			<img src="${ProfileView.user.pictureFileUrl}" />
		</c:if>
		<div style="height: 1em;"></div>
		Upload new profile picture:
		<input type="file" name="userPic" style="margin-left: 10px;">
		<input type="submit" name="submit" value="Save Picture"/>
	</form>
</div>

