<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="actionClass" value="com.breaker.webapp.BreakAction" scope="request"/>
<%@ include file="/includes/misc/mvc.jsp" %>

<html>
<head>
<title>SportsBreaker - Break New Story</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="/style/style.css">
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/prototype.js"></script>
</head>
<body>

<div class="container">
	<c:import url="/includes/navigation/header.jsp"/>

	<c:import url="/includes/navigation/teamTab.jsp"/>
	<div class="break"></div>

	<div class="body">

		<form name="breakform" method="post" action="break.jsp">

		<table>
			<tr>
				<td width="50%">
					<div class="heading"> + Break a New Story</div>

					<c:if test="${not empty BreakView.error}">
						<center><br/><div class="error_text"><c:out value="${BreakView.error}" escapeXml="false"/></div></center>
					</c:if>

					<div class="break"></div>

					<span class="big bold blue">Enter the URL</span>
					<div>
						<input type="text" name="url" size="80" value="<c:out value="${BreakView.url}"/>"><br/>
						<input type="submit" name="checkDuplicate" value="Check for Duplicate" />
					</div>

					<div class="break"></div>
					
					<span class="big bold blue">Enter the Story Title</span>
					<span class="blue"><i>(Max 75 Characters)</i></span>
					<div>
						<input type="text" name="title" size="80" value="<c:out value="${BreakView.title}"/>" maxlength="75">
					</div>

					<div class="break"></div>

					<span class="big bold blue">Enter the Story Description</span>
					<span class="blue"><i>(Max 500 Characters)</i></span>
					<div>
						<textarea rows="10" cols="50" name="desc"><c:out value="${BreakView.description}"/></textarea>
					</div>

					<div class="break"></div>
				</td>

				<td valign="top">
					<div class="text_box">
						<p class="bigger bold"><b>Submission Tips</b></p>
						<hr class="across_table">
						<p><span class="bold">Avoid a Duplicate</span><br>
							If your exact story has already been submitted by another
							user, yours won't go through. So be sure  to click "Check for Duplicate" before
							you go through the whole submission process.</p>
						<p><span class="bold">Link Directly to the Story</span><br>
							If your story is on ESPN.com, don't just send us to 
							www.espn.com. Be sure to copy and paste the entire URL so that we are linked to 
							the actual story.</p>
						<p><span class="bold">Only Include Relevant Teams</span><br>
							If a story about Texas A&amp;M briefly mentions Texas
							along with others as one team on the schedule this year, it makes the most sense 
							to only assign the story to Texas A&amp;M. But if the story is about the historyic 
							rivalry that has grown between the two schools, then you should assign it to 
							t.u. as well.</p>
					</div>
				</td>
			</tr>
		</table>


		<div class="big_bold blue"><p>Assign Categories and Teams</p></div>

		<table>
			<tr>
				<td valign="top">
					<select style="width: 300px;" name="sport" id="sport" onchange="selectSport();"
						<c:if test="${BreakView.defaultCategoryId gt 0}">readonly</c:if>>
						<option value=""> - Select a Sport - </option>
						<c:forEach items="${BreakView.categories}" var="category" varStatus="status">
							<option value="<c:out value="${category.id}"/>" 
								<c:if test="${category.id eq BreakView.selectedCategoryId}">selected</c:if>>
								<c:out value="${category.name}"/>
							</option>
						</c:forEach>
					</select>
					<a href="javascript:submitSport();">Add all teams in this sport</a>
					<input type="hidden" id="allInSport" name="allInSport" value="false" />

					<div style="height: .5em;"></div>

					<select name="conference" id="conference" style="width: 300px;" disabled>
						<option value=""> - Select a Conference - </option>
					</select>
					<a href="javascript:submitConference();">Add all teams in this conference</a>
					<input type="hidden" id="allInConference" name="allInConference" value="false" />

					<br/><img src="/images/spacer.gif" height="1px" width="100px"> -- or -- <br/>

					<select name="team_1" id="team_1" style="width: 300px;" disabled>
						<option value=""> - Select a Team - </option>
					</select> <a href="javascript:submitTeam();">Add this team</a>

					<div style="height: .5em;"></div>

				</td>
				<td valign="top" style="padding-left: 2em;">


					<b><u>Selected Teams</u></b><br/>

					<span id="selectedTeamList">
						<c:if test="${not empty BreakView.selectedTeams}">		
							<c:forEach items="${BreakView.selectedTeams}" var="team" varStatus="status">
								${team.displayName}<br>
							</c:forEach>
						</c:if>
					</span>
					<span id="noTeamSelected">
						<c:if test="${empty BreakView.selectedTeams}">
							Use the drop-down list below to add<br/>the teams that this story relates to.
						</c:if>
					</span>
					<br/>
					<a href="javascript:changeSport();">Clear selected teams</a><br/>

				</td>
			</tr>
		</table>

		<input type="hidden" name="selectedTeamIds" id="selectedTeamIds" value="<c:out value="${BreakView.selectedTeamIds}"/>">
		<input type="submit" name="breakform_submit" value="&nbsp;&nbsp;&nbsp;Break It!&nbsp;&nbsp;&nbsp;">
		</form>

	</div>

<c:import url="/includes/navigation/footer.jsp"/>

</div>



<script type="text/javascript"><!--
<c:if test="${BreakView.selectedCategoryId > 0}">
	selectSport();
</c:if>

function selectSport()
{
	$('conference').disabled = true;
	$('team_1').disabled = true;
	//$('team_2').disabled = true;

	var index = $('sport').selectedIndex;
	var categoryId = $('sport')[index].value;
	var url = '/servlet/BreakStoryHelper?categoryId='+categoryId;
	new Ajax.Request(url,
	{
    	method:'get',
    	onSuccess: function(transport){
			removeElementsFromSelect($('conference'), 1);
			removeElementsFromSelect($('team_1'), 1);
			//removeElementsFromSelect($('team_2'), 1);

			var responseText = transport.responseText;
			var xmlObject = Try.these(
				function() { 	return new DOMParser().parseFromString(responseText, 'text/xml'); },
				function() { 	var xmldom = new ActiveXObject('Microsoft.XMLDOM'); 
								xmldom.loadXML(responseText); return xmldom; });
			var teams = xmlObject.getElementsByTagName('team');
			for (i = 0 ; i < teams.length ; i++)
			{
				var team = teams[i];
				var id = team.getElementsByTagName("id")[0].firstChild.nodeValue;
				var teamName = team.getElementsByTagName("display_name")[0].firstChild.nodeValue;
				appendOptionToSelectElement($('team_1'), id, teamName, false);

				// We don't always have a conference, so surround this with a try-catch
				try {
					var conference = team.getElementsByTagName("conference")[0].firstChild.nodeValue;
					var subconference = team.getElementsByTagName("subconference")[0].firstChild.nodeValue;
					if(subconference && subconference != null && subconference != undefined && subconference != 'null')
						conference = conference + '---' + subconference;
					appendOptionToSelectElement($('conference'), conference, conference, false);
				} catch (err) {
					// swallow
				}
			}

			var conferenceSelect = $('conference');
			conferenceSelect.options[0] = null;
			var arr = new Array(conferenceSelect.options.length);
			for(var i=0; i < conferenceSelect.options.length; i++) {
				arr[i] = conferenceSelect.options[i].text;
			}
			arr.sort();
			removeElementsFromSelect(conferenceSelect, 0);
			conferenceSelect.options[0] = new Option(' - Select a Conference - ', '', false, false);
			for(var i=0; i < arr.length; i++) {
				conferenceSelect.options[i+1] = new Option(arr[i].replace('---', ' - '), arr[i], false, false);
			}

			$('conference').disabled = false;
			$('team_1').disabled = false;
			// $('changeSport').style.visibility = 'visible';
			//$('team_2').disabled = false;
		},
    	onFailure: function(){ alert('Unable to connect to server...') }
	});
}

function submitSport() {
	$('allInSport').value = 'true';
	$('conference').value = '';
	$('team_1').value = '';
	submit();
}

function submitConference(){
	$('allInConference').value = 'true';
	$('team_1').value = '';
	submit();
}

function submitTeam() {
	if($('team_1').value != '') {
		$('conference').value = '';
		var teamSelectedIndex = $('team_1').selectedIndex;
		$('selectedTeamList').innerHTML = $('selectedTeamList').innerHTML + $('team_1').options[teamSelectedIndex].text + '<br>';
		$('selectedTeamIds').value = $('selectedTeamIds').value + ',' + $('team_1').value;
		$('team_1').value = '';
	}
	$('noTeamSelected').style.display = 'none';
	$('noTeamSelected').style.visibility = 'hidden';
}

function changeSport() {
	//$('changeSport').style.visibility = 'hidden';

	$('sport').disabled = false;
	$('conference').disabled = true;
	$('team_1').disabled = true;

	$('sport').options[0].selected = true;
	$('conference').options[0].selected = true;
	$('team_1').options[0].selected = true;
	
	$('selectedTeamIds').value = '';
	$('selectedTeamList').innerHTML = '';
	
	$('noTeamSelected').style.display = 'block';
	$('noTeamSelected').style.visibility = 'visible';
}

function submit() {
	document.breakform.submit();
}
//-->
</script>


</body>
</html>

