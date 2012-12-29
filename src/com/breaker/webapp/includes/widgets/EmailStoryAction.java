package com.breaker.webapp.includes.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.newsstory.StoryBean;
import com.breaker.newsstory.StoryFactory;
import com.breaker.utils.MailSender;
import com.breaker.utils.RequestUtils;
import com.breaker.utils.StringUtils;
import com.breaker.webapp.MVCActionInterface;
import com.breaker.webapp.MVCRedirector;

public class EmailStoryAction implements MVCActionInterface {

    public MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response) {
        EmailStoryView view = new EmailStoryView();

        int storyId = RequestUtils.getParameterAsInt(request, "emailStoryId", 0);
        if (storyId > 0) {

            String to = RequestUtils.getCleanedParameter(request, "to");

            List<String> emails = parseEmails(to);
            if (emails == null) {
                view.setError("At least one of the emails provided seems to be invalid.");
            } else if (emails.isEmpty()) {
                view.setError("You must provide an email addres for us to send to.");
            } else {
                String from = RequestUtils.getCleanedParameter(request, "from");
                if (StringUtils.isEmpty(from))
                    from = "Your Friend";
                String msg = RequestUtils.getCleanedParameter(request, "msg");
                StoryBean story = StoryFactory.getStoryById(storyId);
                if (story != null) {
                    String subj = "A Sports Story " + from + " Thought You Might Like";
                    StringBuilder msgSb = new StringBuilder();
                    msgSb.append("Sports Fan,<br/><br/>");
                    msgSb.append(from + " thought you might like the following story on SportsBreaker.com:<br/>");
                    msgSb.append("<a href=\"http://www.sportsbreaker.com/story.jsp?id=" + storyId + "\">");
                    msgSb.append(story.getTitle());
                    msgSb.append("</a><br/><br/>");

                    if (!StringUtils.isEmpty(msg)) {
                        msgSb.append(msg + "<br/><br/>");
                    }

                    MailSender.sendMail(emails, subj, msgSb.toString(), true);
                    view.setEmailed(true);
                    view.setError("");
                }
            }
        }

        if (storyId == 0) {
            StoryBean story = (StoryBean) request.getAttribute("story");
            if (story != null) {
                storyId = story.getStoryId();
            }
        }

        view.setStoryId(storyId);
        request.setAttribute("EmailStoryView", view);
        return null;
    }

    private List<String> parseEmails(String to) {
        List<String> emails = new ArrayList<String>();
        StringTokenizer tokenizer = new StringTokenizer(to, " ,;\n\r\t");
        while (tokenizer.hasMoreTokens()) {
            String email = tokenizer.nextToken().trim();
            if (StringUtils.verifyEmailFormat(email)) {
                emails.add(email);
            } else
                return null;
        }
        return emails;
    }

}
