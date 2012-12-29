// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 12/16/2007 2:59:01 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) fieldsfirst 
// Source File Name:   RSSFeedTester.java

package com.breaker.testing;

import com.breaker.rss.reader.FeedFactory;

public class RSSFeedTester
{

    public RSSFeedTester()
    {
    }

    public static void main(String args[])
    {
        String testUrl = "http://feeds.sportsline.com/cbssportsline/nfl_news?format=xml";
        FeedFactory.getFeed(testUrl);
    }
}