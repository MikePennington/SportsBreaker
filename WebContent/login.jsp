<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="actionClass" value="com.breaker.webapp.LoginAction" scope="request"/>
<%@ include file="/includes/misc/mvc.jsp" %>

<html>
<head>
<title>SportsBreaker - Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="/style/style.css" />
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/prototype.js"></script>
</head>
<body>
<div class="container">

<c:import url="/includes/navigation/header.jsp"/>

<form name="loginform" method="post">
<input type="hidden" name="returnUrl" value="<c:out value="${param.returnUrl}" />">
<center>
<div class="grey_box" style="width: 50%;">
<table>

	<c:if test="${not empty LoginView.error}">
		<tr>
			<td colspan="2" class="error_text"><c:out value="${LoginView.error}"/></td>
		</tr>
	</c:if>
	<c:if test="${not empty LoginView.message}">
		<tr>
			<td colspan="2" class="message_text"><c:out value="${LoginView.message}"/></td>
		</tr>
	</c:if>

	<tr>
		<td colspan="2" class="grey_larger_font">Existing Users</td>
	</tr>
	<tr>
		<td>Email:</td>
		<td><input type="text" name="email" size="30"></td>
	</tr>
	<tr>
		<td>Password:</td>
		<td><input type="password" name="password" size="30"></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="checkbox" name="alwaysLoggedIn" checked>
			Remember me on this computer
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="submit" name="loginSubmit" value="&nbsp;&nbsp;&nbsp;Login&nbsp;&nbsp;&nbsp;">
		</td>
	</tr>
	<tr>
		<td colspan="2"><br/>Don't have an account? <a href="signup.jsp">Sign Up!</a></td>
	</tr>
	<tr>
		<td colspan="2"><a href="javascript: grayOut(true, ''); show('forgotPassword'); absoluteCenter('forgotPassword');">I forgot my password!</a></td>
	</tr>
</table>
</div>
</center>
</form>

<c:import url="/includes/navigation/footer.jsp"/>

</div>



<div id="forgotPassword" class="lightbox" style="visibility: hidden; width: 400px;">
	<form name="forgotPasswordForm">
	<a href="javascript: grayOut(false, ''); hide('forgotPassword');">close</a>
	<p>Forgot your password, huh? That's cool, it happens to the best of us. Fill out your email address
	below and we'll email your password to you.</p>
	Email:&nbsp;&nbsp;
	<input type="text" name="forgotPasswordEmail" style="width: 200px;">&nbsp;&nbsp;
	<input type="submit" name="forgotPasswordSubmit" value="Go!" class="submitButton">
	</form>
</div>


</body>
</html>