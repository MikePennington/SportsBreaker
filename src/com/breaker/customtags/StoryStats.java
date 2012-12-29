// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 12/8/2007 6:48:43 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) fieldsfirst 
// Source File Name:   StoryStats.java

package com.breaker.customtags;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.TagSupport;

public class StoryStats extends TagSupport
{

    private static final long serialVersionUID = 1L;

    public StoryStats()
    {
    }

    public int doStartTag()
        throws JspException
    {
        HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
        java.util.Locale locale = req.getLocale();
        DateFormat df = SimpleDateFormat.getDateInstance(0, locale);
        String date = df.format(new Date());
        try
        {
            JspWriter out = pageContext.getOut();
            out.print(date);
        }
        catch(IOException ioe)
        {
            throw new JspException((new StringBuilder("I/O Error : ")).append(ioe.getMessage()).toString());
        }
        return 0;
    }
}