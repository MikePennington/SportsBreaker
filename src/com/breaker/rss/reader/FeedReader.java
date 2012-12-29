package com.breaker.rss.reader;

import com.breaker.rss.Channel;
import com.breaker.rss.Image;
import com.breaker.rss.Item;
import com.breaker.utils.XMLUtils;

import org.w3c.dom.*;

public class FeedReader
{

    private RSSFeed feed;

    protected FeedReader(Document document)
    {
        feed = null;
        if (document != null)
            parse(document);
    }

    public RSSFeed getRssFeed()
    {
        return feed;
    }

    private void parse(Document document)
    {
        Element rssElement = document.getDocumentElement();
        if ("rss".equalsIgnoreCase(rssElement.getNodeName()))
            feed = parseNode_RSS(rssElement);
    }

    private RSSFeed parseNode_RSS(Element node)
    {
        RSSFeed rssFeed = new RSSFeed();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++)
        {
            Node childNode = childNodes.item(i);
            if (childNode != null && "channel".equalsIgnoreCase(childNode.getNodeName()))
                rssFeed.setChannel(parseNode_CHANNEL(childNode));
        }

        return rssFeed;
    }

    private Channel parseNode_CHANNEL(Node node)
    {
        Channel channel = new Channel();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++)
        {
            Node childNode = childNodes.item(i);
            if (childNode != null)
            {
                String nodeName = childNode.getNodeName();
                if ("title".equalsIgnoreCase(nodeName))
                    channel.setTitle(XMLUtils.parseNodeAsText(childNode));
                else if ("description".equalsIgnoreCase(nodeName))
                    channel.setDescription(XMLUtils.parseNodeAsText(childNode));
                else if ("link".equalsIgnoreCase(nodeName))
                    channel.setLink(XMLUtils.parseNodeAsText(childNode));
                else if ("language".equalsIgnoreCase(nodeName))
                    channel.setLanguage(XMLUtils.parseNodeAsText(childNode));
                else if ("copyright".equalsIgnoreCase(nodeName))
                    channel.setCopyright(XMLUtils.parseNodeAsText(childNode));
                else if ("image".equalsIgnoreCase(nodeName))
                    channel.setImage(parseNode_IMAGE(childNode));
                else if ("item".equalsIgnoreCase(nodeName))
                    channel.addItem(parseNode_ITEM(childNode));
            }
        }

        return channel;
    }

    private Item parseNode_ITEM(Node node)
    {
        Item item = new Item();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++)
        {
            Node childNode = childNodes.item(i);
            if (childNode != null)
            {
                String nodeName = childNode.getNodeName();
                if ("title".equalsIgnoreCase(nodeName))
                    item.setTitle(XMLUtils.parseNodeAsText(childNode));
                else if ("description".equalsIgnoreCase(nodeName))
                    item.setDescription(XMLUtils.parseNodeAsText(childNode));
                else if ("link".equalsIgnoreCase(nodeName))
                    item.setLink(XMLUtils.parseNodeAsText(childNode));
                else if ("guid".equalsIgnoreCase(nodeName))
                    item.setGuid(XMLUtils.parseNodeAsText(childNode));
                else if ("pubDate".equalsIgnoreCase(nodeName))
                    item.setPublishedDate(XMLUtils.parseNodeAsDate(childNode));
                else if ("media:content".equalsIgnoreCase(nodeName))
                    item.setMedia_content(XMLUtils.getAttributeFromNode(childNode, "url"));
                else if ("media:credit".equalsIgnoreCase(nodeName))
                    item.setMedia_credit(XMLUtils.getAttributeFromNode(childNode, "url"));
                else if ("media:text".equalsIgnoreCase(nodeName))
                    item.setMedia_text(XMLUtils.getAttributeFromNode(childNode, "url"));
            }
        }

        return item;
    }

    private Image parseNode_IMAGE(Node node)
    {
        Image image = new Image();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++)
        {
            Node childNode = childNodes.item(i);
            if (childNode != null)
            {
                String nodeName = childNode.getNodeName();
                if ("url".equalsIgnoreCase(nodeName))
                    image.setUrl(XMLUtils.parseNodeAsText(childNode));
                else if ("title".equalsIgnoreCase(nodeName))
                    image.setTitle(XMLUtils.parseNodeAsText(childNode));
                else if ("link".equalsIgnoreCase(nodeName))
                    image.setLink(XMLUtils.parseNodeAsText(childNode));
                else if ("width".equalsIgnoreCase(nodeName))
                    image.setWidth(XMLUtils.parseNodeAsInt(childNode));
                else if ("height".equalsIgnoreCase(nodeName))
                    image.setHeight(XMLUtils.parseNodeAsInt(childNode));
            }
        }

        return image;
    }
}