package com.breaker.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class MailSender {

    private static Logger       log         = Logger.getLogger(MailSender.class);

    private static final String SMTP_HOST   = "localhost";
    private static final String FOOTER_TEXT = "\n\nSincerely,\nSportsBreaker.com";
    private static final String FOOTER_HTML = "<br/><br/>Sincerely,<br/><a href=\"http://www.sportsbreaker.com\">SportsBreaker.com</a>";

    public static final String  EMAIL_INFO  = "info@sportsbreaker.com";

    public static void sendMail(String to, String subject, String message, boolean isHtml) {
        List<String> toList = new ArrayList<String>();
        toList.add(to);
        sendMail(toList, subject, message, isHtml);
    }

    public static void sendMail(List<String> toList, String subject, String message, boolean isHtml) {
        try {

            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_HOST);
            Session s = Session.getInstance(props, null);

            MimeMessage mimeMessage = new MimeMessage(s);

            InternetAddress fromAddress = new InternetAddress(EMAIL_INFO);
            mimeMessage.setFrom(fromAddress);
            for (String to : toList) {
                InternetAddress toAddress = new InternetAddress(to);
                mimeMessage.addRecipient(Message.RecipientType.TO, toAddress);
            }

            mimeMessage.setSubject(subject);
            if (isHtml) {
                mimeMessage.setContent(message + FOOTER_HTML, "text/html");
            } else {
                mimeMessage.setText(message + FOOTER_TEXT);
            }

            Transport.send(mimeMessage);
        } catch (Exception e) {
            log.error("Error sending mail", e);
        }
    }

}
