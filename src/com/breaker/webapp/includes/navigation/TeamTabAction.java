package com.breaker.webapp.includes.navigation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.team.TeamTabBean;
import com.breaker.team.TeamTabsDBUtils;
import com.breaker.user.LoginUtils;
import com.breaker.user.User;
import com.breaker.utils.BrowserDetector;
import com.breaker.utils.RequestUtils;
import com.breaker.webapp.MVCActionInterface;
import com.breaker.webapp.MVCRedirector;
import com.breaker.webapp.includes.navigation.TeamTabView.TeamTab;

public class TeamTabAction implements MVCActionInterface {

    public MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response) {
        TeamTabView view = new TeamTabView();

        User user = LoginUtils.getUserObject(request, response);
        if (user != null) {
            int teamId = RequestUtils.getParameterAsInt(request, "teamId", 0);
            List<TeamTabBean> teams = TeamTabsDBUtils.getTeamListByUser(user.getUserId());
            List<TeamTab> teamTabs = new ArrayList<TeamTab>();
            for (int i = 0; teams != null && i < teams.size(); i++) {
                TeamTabBean team = teams.get(i);
                TeamTab teamTab = view.new TeamTab();
                teamTab.setTeamId(team.getTeamId());
                teamTab.setTeamName(team.getName());
                if (teamId == team.getTeamId())
                    teamTab.setSelected(true);
                teamTabs.add(teamTab);
            }

            view.setTeamTabs(teamTabs);
        }

        BrowserDetector browser = new BrowserDetector(request);
        if (!browser.isIeAny()) {
            view.setBrowserStyleHeight("21px");
        }
        request.setAttribute("TeamTabView", view);

        return null;
    }
}
