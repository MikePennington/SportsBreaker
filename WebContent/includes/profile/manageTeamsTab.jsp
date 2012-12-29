<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<table border="0" cellpadding="0" cellspacing="0" class="profile_body">
	<tr>
		<td width="50%" valign="top" style="padding-right: 2em;">
			<div class="bigger bold blue">Manage Teams</div>
			<form name="manage_team_form" action="profile.jsp?tab=teams" method="post">
			<input type="hidden" name="rename" value="">
			<input type="hidden" name="remove" value="">
			<input type="hidden" name="move_up" value="">
			<input type="hidden" name="move_down" value="">
			<input type="hidden" name="manageTeamSubmit" value="submit">
			<span class="big">
			<c:if test="${not empty ProfileView.teamTabs}">
				<c:forEach items="${ProfileView.teamTabs}" var="teamTab" varStatus="status">
					<c:choose>
						<c:when test="${ProfileView.teamIdToRename == teamTab.teamId}">
							<input type="text" name="renameTeamId<c:out value="${teamTab.teamId}"/>" value="<c:out value="${teamTab.name}"/>">
							<input type="submit" name="renameTeamSubmit" value="Rename">
						</c:when>
						<c:otherwise>
							<span class="big"><c:out value="${teamTab.name}"/></span>
							<i>(
							<a href="javascript:rename(<c:out value="${teamTab.teamId}"/>);">Rename</a> |
							<a href="javascript:remove(<c:out value="${teamTab.teamId}"/>);">Remove</a>
							<c:if test="${not (status.first and status.last)}"> | </c:if> 
							<c:if test="${not status.first}">
								<a href="javascript:moveUp(<c:out value="${teamTab.teamId}"/>);">Move Up</a>
							</c:if>
							<c:if test="${not status.first and not status.last}">
								|
							</c:if>
							<c:if test="${not status.last}">
								 <a href="javascript:moveDown(<c:out value="${teamTab.teamId}"/>);">Move Down</a>)
							</c:if>
							<c:if test="${status.last}">
								 )
							</c:if>
							</i>
						</c:otherwise>
					</c:choose>
					<br>
				</c:forEach>
			</c:if>
			<c:if test="${empty ProfileView.teamTabs}">
				You don't have any teams in your profile.<br>Use the controls to the right to add your favorite teams.
			</c:if>
			</span>
			</form>
		</td>
		<td width="50%" valign="top">
			<div valign="top" class="bigger bold blue">Add New Team</div>
			<form name="add_team_form" action="profile.jsp?tab=teams" method="post">
			<select style="width: 300px;" name="sport" id="sport" onchange="selectSport();">
				<option value=""> - Select a Sport - </option>
				<c:forEach items="${ProfileView.categories}" var="category" varStatus="status">
					<option value="${category.id}" <c:if test="${ProfileView.categoryIdSelected eq category.id}">selected</c:if>>${category.name}</option>
				</c:forEach>
			</select>
			
			<br><img src="/images/spacer.gif" height="5"><br>
			
			<select name="new_team" id="new_team" style="width: 300px;" onchange="submit();" <c:if test="${empty ProfileView.teams}">disabled</c:if>>
				<option value=""> - Select a Team - </option>
				<c:if test="${not empty ProfileView.teams}">
					<c:forEach items="${ProfileView.teams}" var="team" varStatus="status">
						<option value="${team.teamId}" <c:if test="${ProfileView.teamIdSelected eq team.teamId}">selected</c:if>>${team.noSportDisplayName}</option>
					</c:forEach>
				</c:if>
			</select>
			<input type="hidden" name="addTabSelectSport" value="true" />

			<br><img src="/images/spacer.gif" height="5"><br>

			<span class="big">Tab Name: <input type="text" name="newTeamName" value="${ProfileView.newTabName}" maxlength="100"></span>
			
			<br><img src="/images/spacer.gif" height="5"><br>

			<input type="submit" name="addTeamSubmit" id="addTeamSubmit" value="Add Tab">

			</form>
		</td>
	</tr>
</table>



<script type="text/javascript"><!--

function rename(teamId)
{
	document.manage_team_form.rename.value = teamId;
	document.manage_team_form.submit();
}

function remove(teamId)
{
	document.manage_team_form.remove.value = teamId;
	document.manage_team_form.submit();
}

function moveUp(teamId)
{
	document.manage_team_form.move_up.value = teamId;
	document.manage_team_form.submit();
}

function moveDown(teamId)
{
	document.manage_team_form.move_down.value = teamId;
	document.manage_team_form.submit();
}

function selectSport()
{
	var index = $('sport').selectedIndex;
	var categoryId = $('sport')[index].value;
	var selectedTeamId = <c:out value="${ProfileView.teamIdSelected}" />;
	var url = '/servlet/BreakStoryHelper?categoryId='+categoryId;
	new Ajax.Request(url,
	{
    	method:'get',
    	onSuccess: function(transport){
			removeElementsFromSelect($('new_team'), 1);
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
				if(id == selectedTeamId)
					appendOptionToSelectElement($('new_team'), id, teamName, true);
				else 
					appendOptionToSelectElement($('new_team'), id, teamName, false);
			}
			//$('conference').disabled = false;
			$('new_team').disabled = false;
		},
    	onFailure: function(){ alert('Unable to connect to server...') }
	});
}
//-->
</script>
