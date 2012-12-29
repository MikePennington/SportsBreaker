package com.breaker.navigation;

public class NavItemBean
{
    private String url;
    private String text;

    public NavItemBean(String url, String text)
    {
        this.url = url;
        this.text = text;
    }

    public String getText()
    {
        return text;
    }

    public String getUrl()
    {
        return url;
    }

}
