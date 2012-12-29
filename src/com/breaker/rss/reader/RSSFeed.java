package com.breaker.rss.reader;

import com.breaker.rss.Channel;

public class RSSFeed
{

    private double  version;
    private Channel channel;

    public Channel getChannel()
    {
        return channel;
    }

    public void setChannel(Channel channel)
    {
        this.channel = channel;
    }

    public double getVersion()
    {
        return version;
    }

    public void setVersion(double version)
    {
        this.version = version;
    }
}