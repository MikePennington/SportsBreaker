<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="actionClass" value="com.breaker.webapp.LogoutAction" scope="request"/>
<%@ include file="/includes/misc/mvc.jsp" %>

<html>
<head>
<title>Benchd - Login</title>
<meta http-equiv="Content-Type" content="text/html;">
<link rel="stylesheet" type="text/css" href="/style/style.css" />
</head>
<body>
<div class="container">

<c:import url="/includes/navigation/header.jsp"/>

<center>
You have been logged out.
</center>

<c:import url="/includes/navigation/footer.jsp"/>

</div>
</body>
</html>