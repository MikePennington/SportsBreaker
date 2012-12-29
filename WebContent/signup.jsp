<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="actionClass" value="com.breaker.webapp.SignupAction" scope="request"/>
<%@ include file="/includes/misc/mvc.jsp" %>

<html>
<head>
<title>SportsBreaker - Signup</title>
<meta http-equiv="Content-Type" content="text/html;">
<link rel="stylesheet" type="text/css" href="/style/style.css" />
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/prototype.js"></script>

<!-- For jscalendar -->
<link rel="stylesheet" type="text/css" media="all" href="/js/jscalendar/calendar-blue2.css" />
<script type="text/javascript" src="/js/jscalendar/calendar.js"></script>
<script type="text/javascript" src="/js/jscalendar/lang/calendar-en.js"></script>
<script type="text/javascript" src="/js/jscalendar/calendar-setup.js"></script>

</head>
<body>
<div class="container">

<c:import url="/includes/navigation/header.jsp"/>

<form name="signupform" method="post">
<center>
<div class="grey_box" style="width: 60%;">
<c:if test="${not empty SignupView.errorMessage}">
	<div class="error_text"><c:out value="${SignupView.errorMessage}" escapeXml="false" /></div><br>
</c:if>
<table>
	<tr>
		<td colspan="2" class="bigger bold blue">Get a SportsBreaker Account</td>
	</tr>
	<tr>
		<td colspan="2">Fields marked with a <font color="red">*</font> are required</td>
	</tr>
	<tr>
		<td align="right">Username<font color="red">*</font></td>
		<td><input type="text" name="username" size="30" value="${SignupView.username}" style="margin-left: 10px; width: 250px"></td>
	</tr>
	<tr>
		<td align="right">Email<font color="red">*</font></td>
		<td><input type="text" name="email" size="30" value="${SignupView.email}" style="margin-left: 10px; width: 250px"></td>
	</tr>
	<tr>
		<td align="right">Verify Email<font color="red">*</font></td>
		<td><input type="text" name="verify_email" size="30" value="${SignupView.verifyEmail}" style="margin-left: 10px; width: 250px"></td>
	</tr>
	<tr>
		<td align="right">Password<font color="red">*</font></td>
		<td><input type="password" name="password" size="30" value="${SignupView.password}" style="margin-left: 10px; width: 250px"></td>
	</tr>
	<tr>
		<td align="right">Verify Password<font color="red">*</font></td>
		<td><input type="password" name="verify_password" size="30" value="${SignupView.verifyPassword}" style="margin-left: 10px; width: 250px"></td>
	</tr>
	<tr>
		<td colspan="2" class="bigger bold blue"><div style="height: 10px;"></div>Optional Information</td>
	</tr>
	<tr>
		<td align="right">Real Name</td>
		<td><input type="text" name="real_name" size="30" value="${SignupView.user.realName}" style="margin-left: 10px; width: 250px"></td>
	</tr>
	<tr>
		<td align="right">Hometown</td>
		<td><input type="text" name="hometown" size="30" value="${SignupView.user.hometown}" style="margin-left: 10px; width: 250px"></td>
	</tr>
	<tr>
		<td align="right">Current Location</td>
		<td><input type="text" name="location" size="30" value="${SignupView.user.hometown}" style="margin-left: 10px; width: 250px"></td>
	</tr>
	<tr>
		<td align="right">College</td>
		<td><input type="text" name="college" size="30" value="${SignupView.user.college}" style="margin-left: 10px; width: 250px"></td>
	</tr>
	<tr>
		<td align="right">High School</td>
		<td><input type="text" name="high_school" size="30" value="${SignupView.user.highSchool}" style="margin-left: 10px; width: 250px"></td>
	</tr>
	<tr>
		<td align="right">Zip Code</td>
		<td><input type="text" name="zip_code" size="30" value="${SignupView.user.zipCode}" style="margin-left: 10px; width: 250px"></td>
	</tr>
	<tr>
		<td align="right">Gender</td>
		<td>
			<select name="gender" style="margin-left: 10px; width: 250px">
				<option> - Please Select - </option>
				<option value="M" <c:if test="${SignupView.user.gender eq 'M'}">selected</c:if>>Male</option>
				<option value="F" <c:if test="${SignupView.user.gender eq 'F'}">selected</c:if>>Female</option>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">Birthday</td>
		<td>
			<input type="text" name="birthday" id="birthday" value="${SignupView.user.birthdayAsString}" style="margin-left: 10px; width: 250px" />
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
		<td colspan="2" class="bigger bold blue"><div style="height: 10px;"></div>Are You Human?</td>
	</tr>
	<tr>
		<td align="left" valign="top">
			<div style="width: 90px;">
				Type the two words to verify that you're not made of 0's and 1's. <font color="red">*</font>
			</div>
		</td>
		<td>
			<script type="text/javascript"
   				src="http://api.recaptcha.net/challenge?k=6Le61QIAAAAAAK4MmhcPBCfqCwI_1T4a2qU7oDsA">
			</script>
			<noscript>
			   <iframe src="http://api.recaptcha.net/noscript?k=6Le61QIAAAAAAK4MmhcPBCfqCwI_1T4a2qU7oDsA"
			       height="300" width="500" frameborder="0"></iframe><br>
			   <textarea name="recaptcha_challenge_field" rows="3" cols="40">
			   </textarea>
			   <input type="hidden" name="recaptcha_response_field" 
			       value="manual_challenge">
			</noscript>
		</td>
	</tr>
	<tr>
		<td align="right">&nbsp;</td>
		<td><input type="submit" name="<c:out value="${SignupView.submitButtonName}"/>" value="<c:out value="${SignupView.submitButtonValue}"/>"></td>
	</tr>
</table>
</div>
</center>
</form>

<c:import url="/includes/navigation/footer.jsp"/>

</div>
</body>
</html>
