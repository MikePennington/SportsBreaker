package com.breaker.utils;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * A set of utilies used for getting and setting items from a request object
 * 
 * @author Mike Pennington
 */
public class RequestUtils {

    public static String getFullUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if (!StringUtils.isEmpty(queryString))
            url = (new StringBuilder(String.valueOf(url))).append("?").append(queryString)
                    .toString();
        return url;
    }

    public static boolean getSessionObjectAsBoolean(HttpServletRequest request, String name,
            boolean defaultValue) {
        boolean value = defaultValue;
        try {
            HttpSession session = request.getSession(true);
            Boolean booleanObj = (Boolean) session.getAttribute(name);
            if (booleanObj == null)
                value = defaultValue;
            else
                value = booleanObj.booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            value = defaultValue;
        }
        return value;
    }

    public static Object getSessionObject(HttpServletRequest request, String name) {
        Object sessionObject = null;
        try {
            HttpSession session = request.getSession(true);
            sessionObject = session.getAttribute(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionObject;
    }

    public static void removeSessionObject(HttpServletRequest request, String name) {
        try {
            HttpSession session = request.getSession(true);
            session.removeAttribute(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean setSessionAttribute(HttpServletRequest request, String name, Object object) {
        try {
            HttpSession session = request.getSession(true);
            session.setAttribute(name, object);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static int getParameterAsInt(HttpServletRequest request, String name, int defaultValue) {
        int returnValue = defaultValue;
        String intString = getParameterAsString(request, name, null);
        if (intString != null)
            returnValue = StringUtils.toInt(intString, defaultValue);
        return returnValue;
    }

    public static long getParameterAsLong(HttpServletRequest request, String name, long defaultValue) {
        long returnValue = defaultValue;
        String longString = getParameterAsString(request, name, null);
        if (longString != null)
            returnValue = StringUtils.toLong(longString, defaultValue);
        return returnValue;
    }

    public static boolean getParameterAsBoolean(HttpServletRequest request, String name,
            boolean defaultValue) {
        boolean returnValue = defaultValue;
        String boolStr = getParameterAsString(request, name, null);
        if (!StringUtils.isEmpty(boolStr)) {
            if ("true".equalsIgnoreCase(boolStr) || StringUtils.toInt(boolStr, -1) == 1)
                returnValue = true;
            else if ("false".equalsIgnoreCase(boolStr) || StringUtils.toInt(boolStr, -1) == 0)
                returnValue = false;
        }
        return returnValue;
    }

    public static String getParameterAsString(HttpServletRequest request, String name,
            String defaultValue) {
        String value = request.getParameter(name);
        if (StringUtils.isEmpty(value)) {
            if (false && ServletFileUpload.isMultipartContent(request)) {
                try {
                    FileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    List<FileItem> items = upload.parseRequest(request);
                    if (items != null && !items.isEmpty()) {
                        Iterator<FileItem> iter = items.iterator();
                        while (iter.hasNext()) {
                            FileItem item = (FileItem) iter.next();
                            if (item.isFormField()) {
                                String fieldName = item.getFieldName();
                                if (name.equals(fieldName)) {
                                    return item.getString();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                value = defaultValue;
            }
        }
        return value;
    }

    /**
     * Returns the named attributed as an int, or defaultValue if the attribute
     * does not exist or cannot be parsed into an int.
     * 
     * @param request
     * @param name
     * @param defaultValue
     *                The value to be returned if an int by the given name
     *                doesnot exist
     * @return
     */
    public static int getAttributeAsInt(HttpServletRequest request, String name, int defaultValue) {
        int returnValue = defaultValue;
        Object requestObj = request.getAttribute(name);
        if (requestObj != null) {
            if (requestObj instanceof String) {
                String s = (String) requestObj;
                returnValue = StringUtils.toInt(s, defaultValue);
            } else if (requestObj instanceof Integer) {
                returnValue = ((Integer) requestObj).intValue();
            } else if (requestObj instanceof Boolean) {
                boolean boolVal = ((Boolean) requestObj).booleanValue();
                if (boolVal)
                    returnValue = 1;
                else
                    returnValue = 0;
            }
        }
        return returnValue;
    }

    /**
     * Returns the named attributed as an long, or defaultValue if the attribute
     * does not exist or cannot be parsed into an long.
     * 
     * @param request
     * @param name
     * @param defaultValue
     *                The value to be returned if an int by the given name
     *                doesnot exist
     * @return
     */
    public static long getAttributeAsLong(HttpServletRequest request, String name, long defaultValue) {
        long returnValue = defaultValue;
        Object requestObj = request.getAttribute(name);
        if (requestObj != null) {
            if (requestObj instanceof String) {
                String s = (String) requestObj;
                returnValue = StringUtils.toLong(s, defaultValue);
            } else if (requestObj instanceof Long) {
                returnValue = ((Long) requestObj).longValue();
            } else if (requestObj instanceof Boolean) {
                boolean boolVal = ((Boolean) requestObj).booleanValue();
                if (boolVal)
                    returnValue = 1;
                else
                    returnValue = 0;
            }
        }
        return returnValue;
    }

    public static String getPageName(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int index = uri.indexOf("/");
        if (index > -1 && index + 1 < uri.length())
            return uri.substring(index + 1);
        else
            return uri;
    }

    public static String getPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }

    public static String getCleanedParameter(HttpServletRequest request, String name) {
        return StringUtils.unNull(request.getParameter(name)).trim();
    }
}