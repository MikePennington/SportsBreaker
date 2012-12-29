<%@page import="com.breaker.team.TeamBean"%>
<%@page import="java.util.List"%>
<%@page import="com.breaker.team.TeamDBUtils"%>
<%
	List<TeamBean> teamList = TeamDBUtils.getTeamListByCategory(2);
%>

	<select name="conference" id="conference" style="width: 300px;" disabled>
		<option value=""> - Select a Conference (Optional) - </option>
<%		for(int i = 0; teamList != null && i < teamList.size(); i++) { 
			TeamBean team = teamList.get(i);
%>			<option value="<%= team.getTeamId() %>"><%= team.getTeamName() %></option>
<%		} %>
	</select>