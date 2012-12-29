<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>


<html>
<head>
<title>How SportsBreaker Works</title>
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



<div style="padding-left: 50px; width: 800px">

	<div style="position: absolute; margin-left: 700px; margin-top: -5px;">
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
	</div>

	<img src="images/start_step1.jpg" style="padding-bottom: 2px;" />
	<div style="border-bottom: 1px solid #CCCCCC; width: 540px;"></div>
	<a href="login.jsp">
		<img src="images/start_welcome_guest.jpg" style="position: absolute; margin-left: 540px; margin-top: -25px;" />
	</a>
	<div style="width: 500px; padding-top: 5px;">
		Anyone can find news on sportsbreaker, but only registered users can vote,
		comment, and break new stories. It's COMPLETELY FREE and only takes a minute 
		or two to sign up. All you need is an email address and you're home free.
		<div style="height: 10px;"></div>
		<a href="signup.jsp" class="bold">:: Click here to sign up now ::</a>
	</div>



	<div style="height: 30px;"></div>
	<img src="images/start_step2.jpg" style="padding-bottom: 2px;" />
	<div style="border-bottom: 1px solid #CCCCCC; width: 380px;"></div>
	<img src="images/start_tabs.jpg" style="position: absolute; margin-left: 380px; margin-top: -25px;" />
	<div style="width: 350px; padding-top: 5px;">
		We realize that most fans have more than just one team that they're passionate 
		about. That's why we have created Team Tabs, so that every team you follow can 
		be found in one place. For example, let's say you graduated from Texas A&amp;M 
		and lived in Dallas. You would most likely want to tab the Cowboys, Mavericks, 
		Aggies, and the Texas Rangers. You could even throw in a University of Texas tab 
		to keep an eye on your enemy. Then add in a Tiger Woods and Jimmie Johnson tab 
		and you've now got access to seven team and player news feeds, all just one click 
		away.
		<div style="height: 10px;"></div>
		<a href="profile.jsp?tab=teams" class="bold">:: Click here to setup your team tabs now ::</a>
	</div>
	
	
	<div style="height: 30px;"></div>
	<img src="images/start_step3.jpg" style="padding-bottom: 2px;" />
	<div style="border-bottom: 1px solid #CCCCCC; width: 380px;"></div>
	<img src="images/start_break.jpg" style="position: absolute; margin-left: 380px; margin-top: -25px;" />
	<div style="width: 350px; padding-top: 5px;">
		When you come across anything sports-related that you think is interesting, let
		others know about it by submitting it to SportsBreaker. When you break a story,
		you'll be asked to give it a headline, write a short description, and (THIS IS THE 
		IMPORTANT PART!) assign it to a team, group of teams, player, division, conference, 
		or entire league. That way, the story appears on the associated team news feed.
		<div style="height: 10px;"></div>
		<a href="break.jsp" class="bold">:: Click here to break your first story ::</a>
	</div>
	
	
	
	<div style="height: 30px;"></div>
	<img src="images/start_step4.jpg" style="padding-bottom: 2px;" />
	<div style="border-bottom: 1px solid #CCCCCC; width: 380px;"></div>
	<img src="images/start_stats.jpg" style="position: absolute; margin-left: 380px; margin-top: -25px;" />
	<div style="width: 350px; padding-top: 5px;">
		Now that you've registered, customized your page, and broken a story, it's time to 
		start interacting - you can vote on any story found on SportsBreaker. Vote Thumbs Up
		if you like the story, or its good news, or if you're simply an optimistic person. 
		Vote Thumbs Down if you hated the story, or it's terrible news, or if you're just flat
		out angry.
		<div style="height: 10px;"></div>
		<a href="index.jsp" class="bold">:: Click here to view the most popular stories ::</a>
	</div>



	<div style="height: 30px;"></div>
	<img src="images/start_step5.jpg" style="padding-bottom: 2px;" />
	<div style="border-bottom: 1px solid #CCCCCC; width: 380px;"></div>
	<img src="images/start_comment.jpg" style="position: absolute; margin-left: 380px; margin-top: -25px;" />
	<div style="width: 350px; padding-top: 5px;">
		Half the fun of sports is the conversation between fans. When you click on either 
		&qout;Comments&qout; or &qout;More&qout; after the story description, you can see 
		what everyone is talking about. Then reply to whoever you want. Just try your best
		to be civil. 
		<div style="height: 10px;"></div>
		<a href="index.jsp" class="bold">:: Click here to view the most popular stories ::</a>
	</div>


	<div style="height: 30px;"></div>

</div>


<c:import url="/includes/navigation/footer.jsp"/>

</div>
</body>
</html>
