package com.breaker.customtags;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.tagext.TagSupport;

import com.breaker.utils.StringUtils;

public class DateFormatter extends TagSupport
{
    private static final long serialVersionUID = 1L;

    private String            date             = null;

    public int doStartTag()
    {
        try
        {
            Date date = convertStringToDate();
            String formattedDateStr = StringUtils.formatDate(date);
            pageContext.getOut().print(formattedDateStr);
        }
        catch (IOException ioe)
        {
            // Swallow exception
        }
        return SKIP_BODY;
    }

    private Date convertStringToDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat();
        Date date = null;
        try
        {
            date = sdf.parse(this.date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

}
