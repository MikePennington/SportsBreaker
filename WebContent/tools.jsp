<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>


<%@page import="com.breaker.utils.BrowserDetector"%>
<html>
<head>
<title>SportsBreaker - Browser Tools</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="/style/style.css">
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/prototype.js"></script>
</head>
<body>

<div class="container">

<c:import url="/includes/navigation/header.jsp"/>

<c:import url="/includes/navigation/infoPagesTabs.jsp"/>

<div class="break"></div>


<table style="margin-left: 50px;">
	<tr>
		<td width="650px" valign="top">

			<div style="height: 5px;"></div>

			<img src="images/toolbar_header.jpg" />
			<div style="height: 4px;"></div>
			When you add this link to your toolbar, you'll be able to break new stories with the 
			click of a button.

			<div style="height: 15px;"></div>

			1. Drag the following link to your toolbar:
			<a class="bigger bold" style="text-decoration: underline;" href="javascript:location.href='http://www.sportsbreaker.com/break?url='+encodeURIComponent(location.href)+'&title='+encodeURIComponent(document.title.replace(/^\s*|\s*$/g,''))">SportsBreak!</a>

			<div style="height: 15px;"></div>

			<%
			BrowserDetector bd = new BrowserDetector(request);
			if(bd.isFirefoxAny()) { %>
				<img src="images/toolbar_pc_firefox.jpg" />
			<% } else if (bd.isSafariAny()) { %>
				<img src="images/toolbar_mac_safari.jpg" />
			<% } else if(bd.isIeAny()) { %>
				<img src="images/toolbar_pc_ie.jpg" />
			<% } else { %>
				<img src="images/toolbar_mac_firefox.jpg" />
			<% } %>
				
			<div style="height: 15px;"></div>
			
			2. Now, when you click the SportsBreak! tool button, you'll break any story you're
			currently viewing.<br/>
			3. That's all there is to it. Now go break some stories!<br/>
			
			<div style="height: 25px;"></div>
			
			
			<div style="height: 15px; border-top: 1px dashed #000000; width: 524px;"></div>
			
			<img src="images/alert_icon.jpg" /> <b>Can't drag and drop?</b><br/>
			Some Internet Exporer users may not be able to drag and drop the link into their toolbar.
			
			<div style="height: 15px;"></div>
			
			To add this to your IE links toolbar:
			<ol>
				<li>Click the link with your <i>right</i> mouse button and select "Add to Favorites..." from the menu.</li>
				<li>Click OK if a security warning alert pops up (this shows up since the link contains javasctipt).</li>
				<li>If a list of folders is not shown, click the "Create in &gt;&gt;&gt;" button.</li>
				<li>Now select the folder called "Links" and then click OK.</li>
				<li>You should now see the "SportsBreak!" link in your links toolbar.</li>
			</ol>
			
			<div style="height: 15px;"></div>
			
			If not, email us at support@sportsbreaker.com and we will work with you to resolve this issue.

		</td>
		<td valign="top">
			<script type="text/javascript"><!--
			google_ad_client = "pub-6778488833626132";
			/* 160x600, created 2/15/09 */
			google_ad_slot = "4980599471";
			google_ad_width = 160;
			google_ad_height = 600;
			//-->
			</script>
			<script type="text/javascript"
			src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
			</script>
		</td>
	</tr>
</table>




<c:import url="/includes/navigation/footer.jsp"/>

</div>
</body>
</html>
