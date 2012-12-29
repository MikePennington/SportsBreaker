package com.breaker.ajax;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.team.TeamBean;
import com.breaker.team.TeamDBUtils;
import com.breaker.utils.RequestUtils;

public class BreakStoryHelper extends AjaxAbstract {
    private static final long serialVersionUID = 1L;

    /**
     * This does the work.
     */
    public void doAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = RequestUtils.getParameterAsInt(request, "categoryId", 0);
        List<TeamBean> teams = TeamDBUtils.getTeamListByCategory(categoryId);
        if (teams != null && teams.size() > 0) {
            StringBuilder xml = new StringBuilder();
            xml.append("<teams>");
            for (int i = 0; i < teams.size(); i++) {
                xml.append(teams.get(i).toXMLString());
            }
            xml.append("</teams>");

            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(xml.toString());
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
        return;
    }
}
