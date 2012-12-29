<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>

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
		<td width="600px" valign="top" style="padding-right: 50px;">

			<img src="images/sportsbreaker_buttons.jpg" />
			<hr class="across_table" />
			A button is the easiest way to get your contect submitted to SportsBreaker.
			Choose a button you like, and then copy/paste the code into your HTML editor.
			<div class="break"></div>

			<table width="600px">
				<tr>
					<td width="125px"><img src="images/external_buttons/button01.jpg" /></td>
					<td>
						<img src="images/external_buttons/header01.jpg" />
						<div class="code">
							<tt><%= buildButtonCode("button01.jpg") %></tt>
						</div>
						<div style="height: 20px;"></div>
					</td>
				</tr>
				<tr>
					<td width="125px"><img src="images/external_buttons/button02.jpg" /></td>
					<td>
						<img src="images/external_buttons/header02.jpg" />
						<div class="code">
							<tt><%= buildButtonCode("button02.jpg") %></tt>
						</div>
						<div style="height: 20px;"></div>
					</td>
				</tr>
				<tr>
					<td width="125px"><img src="images/external_buttons/button03.jpg" /></td>
					<td>
						<img src="images/external_buttons/header03.jpg" />
						<div class="code">
							<tt><%= buildButtonCode("button03.jpg") %></tt>
						</div>
						<div style="height: 20px;"></div>
					</td>
				</tr>
				<tr>
					<td width="125px"><img src="images/external_buttons/button04.jpg" /></td>
					<td>
						<img src="images/external_buttons/header04.jpg" />
						<div class="code">
							<tt><%= buildButtonCode("button04.jpg") %></tt>
						</div>
						<div style="height: 20px;"></div>
					</td>
				</tr>
				<tr>
					<td width="125px"><img src="images/external_buttons/button05.jpg" /></td>
					<td>
						<img src="images/external_buttons/header05.jpg" />
						<div class="code">
							<tt><%= buildButtonCode("button05.jpg") %></tt>
						</div>
						<div style="height: 20px;"></div>
					</td>
				</tr>
				<tr>
					<td width="125px"><img src="images/external_buttons/button06.jpg" /></td>
					<td>
						<img src="images/external_buttons/header06.jpg" />
						<div class="code">
							<tt><%= buildButtonCode("button06.jpg") %></tt>
						</div>
						<div style="height: 20px;"></div>
					</td>
				</tr>
			</table>
			
			
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



<%!

private String buildButtonCode(String imageName) {
	StringBuilder html = new StringBuilder();
	html.append("&lt;a href=\"http://www.sportsbreaker.com/break\" ");
	html.append("onclick=\"window.location='http://www.sportsbreaker.com/break?url='+");
	html.append("encodeURIComponent(location.href)+'&title='+encodeURIComponent(");
	html.append("document.title.replace(/^\\s*|\\s*$/g,'')); return false\"&gt;");
	html.append("&lt;img src=\"http://www.sportsbreaker.com/images/external_buttons/");
	html.append(imageName);
	html.append("\" border=\"0\" /&gt;&lt;/a&gt;");
	return html.toString();
}

%>
