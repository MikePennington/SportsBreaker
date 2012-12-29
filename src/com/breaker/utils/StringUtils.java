package com.breaker.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtils {
    public static int toInt(String s) {
        return toInt(s, 0);
    }

    public static int toInt(String s, int defaultValue) {
        int i;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            i = defaultValue;
        }
        return i;
    }

    public static long toLong(String s) {
        return toLong(s, 0L);
    }

    public static long toLong(String s, long defaultValue) {
        long i;
        try {
            i = Long.parseLong(s);
        } catch (NumberFormatException e) {
            i = defaultValue;
        }
        return i;
    }

    public static String unNull(String s) {
        if (s == null)
            return "";
        else
            return s;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static String encodeUrl(String s) {
        try {
            s = URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static void appendParameter(StringBuilder url, String name, int value) {
        appendParameter(url, name, String.valueOf(value));
    }

    public static void appendParameter(StringBuilder url, String name, long value) {
        appendParameter(url, name, String.valueOf(value));
    }

    public static void appendParameter(StringBuilder url, String name, String value) {
        if (url.indexOf("?") == -1)
            url.append("?");
        else
            url.append("&");
        url.append(name + "=" + value);
    }

    /**
     * Returns true if the given String seems like an absolute URL
     * 
     * @param url
     */
    public static boolean isAbsoluteURL(String url) {
        if (isEmpty(url))
            return false;

        return url.startsWith("http://") || url.startsWith("https://") || url.startsWith("ftp://");
    }

    /**
     * Verifies that the given String conforms to the format of an email
     * address.
     * 
     * @param s
     */
    public static boolean verifyEmailFormat(String s) {
        if (!isEmpty(s) && s.indexOf("@") >= 1 && s.lastIndexOf(".") >= 3)
            return true;
        else
            return false;
    }

    public static String appendHTMLMessage(String message, String newMessage) {
        if (message == null || message.length() == 0)
            return newMessage;
        else
            return message + "<br/>" + newMessage;
    }

    public static void appendHTMLMessage(StringBuilder message, String newMessage) {
        if (message.length() == 0)
            message.append(newMessage);
        else
            message.append("<br/>" + newMessage);
    }

    /**
     * Formats a Date into yesterday, 4 hours ago, etc.
     * 
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        Calendar compareDate = Calendar.getInstance();
        compareDate.setTime(date);
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        Calendar yesterday = (Calendar) today.clone();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        Calendar rightNow = Calendar.getInstance();
        long millis = rightNow.getTimeInMillis() - compareDate.getTimeInMillis();

        if (compareDate.after(today)) {
            int hoursAgo = rightNow.get(Calendar.HOUR_OF_DAY) - compareDate.get(Calendar.HOUR_OF_DAY);
            int minutesAgo = rightNow.get(Calendar.MINUTE) - compareDate.get(Calendar.MINUTE);
            if (hoursAgo == 0) {
                if (minutesAgo <= 1)
                    minutesAgo = 1;
                return minutesAgo + " minutes ago";
            } else if (millis < 1000 * 60 * 60) {
                return (minutesAgo + 60) + " minutes ago";
            } else if (hoursAgo == 1) {
                return "1 hour ago";
            } else {
                return hoursAgo + " hours ago";
            }
        } else if (millis < 1000 * 60 * 60 * 5) {
            int hoursAgo = rightNow.get(Calendar.HOUR_OF_DAY) - compareDate.get(Calendar.HOUR_OF_DAY);
            return (hoursAgo + 24) + " hours ago";
        } else if (compareDate.after(yesterday)) {
            // SimpleDateFormat sdf = new SimpleDateFormat("h:mm a z");
            return "Yesterday";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
            return sdf.format(date);
        }
    }
}