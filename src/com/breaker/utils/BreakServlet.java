package com.breaker.utils;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.newsstory.StoryBean;
import com.breaker.newsstory.StoryFactory;

public class BreakServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        breakStory(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        breakStory(req, resp);
    }

    private void breakStory(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String url = RequestUtils.getParameterAsString(request, Constants.PARAM_URL, "");
        StoryBean story = StoryFactory.findByURL(url);
        if (story == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/break.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/story.jsp?id=" + story.getStoryId());
            dispatcher.forward(request, response);
        }
    }

}
