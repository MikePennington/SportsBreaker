// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov Date: 12/16/2007 2:20:08 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html - Check often for new version!
// Decompiler options: packimports(3) fieldsfirst
// Source File Name: FeedFactory.java

package com.breaker.rss.reader;

import java.io.IOException;
import java.net.*;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

// Referenced classes of package com.benchd.rss.reader:
// FeedReader, RSSFeed

public class FeedFactory
{

    public FeedFactory()
    {
    }

    public static RSSFeed getFeed(String urlString)
    {
        org.w3c.dom.Document doc;
        URLConnection conn;
        doc = null;
        conn = null;
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            DocumentBuilder parser = null;
            try
            {
                parser = dbf.newDocumentBuilder();
            }
            catch (ParserConfigurationException e)
            {
                e.printStackTrace();
            }
            URL url = new URL(urlString);
            conn = url.openConnection();
            InputSource inputSource = new InputSource(conn.getInputStream());
            doc = parser.parse(inputSource);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
        finally
        {
            conn = null;
        }

        FeedReader feedReader = new FeedReader(doc);
        return feedReader.getRssFeed();
    }
}