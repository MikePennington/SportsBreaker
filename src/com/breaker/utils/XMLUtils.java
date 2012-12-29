package com.breaker.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A set of utilities for reading and writing XML.
 * 
 * @author Mike
 * 
 */
public class XMLUtils {

    private static final SimpleDateFormat FULL_DATE  = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
    private static final SimpleDateFormat FULL_DATE2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a z");

    public static String parseNodeAsText(Node node) {
        if (node == null)
            return null;
        String text = null;
        if (node.getNodeType() == 3) {
            text = node.getNodeValue();
        } else {
            StringBuffer sb = new StringBuffer();
            NodeList children = node.getChildNodes();
            for (int i = 0; children != null && i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child != null) {
                    int childType = child.getNodeType();
                    if (childType == 3 || childType == 4 || childType == 8) {
                        String childText = child.getNodeValue();
                        sb.append(childText);
                    } else {
                        sb.append(parseNodeAsText(child));
                    }
                }
            }

            if (sb.length() > 0)
                text = sb.toString();
        }
        return text;
    }

    public static int parseNodeAsInt(Node node) {
        if (node == null) {
            return 0;
        } else {
            String text = parseNodeAsText(node);
            return StringUtils.toInt(text);
        }
    }

    public static Date parseNodeAsDate(Node node) {
        if (node == null)
            return null;
        String text = parseNodeAsText(node);

        Date date = null;
        try {
            date = FULL_DATE.parse(text);
        } catch (ParseException e) {
            try {
                date = FULL_DATE2.parse(text);
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
        return date;
    }

    public static String getAttributeFromNode(Node node, String attributeName) {
        if (node == null || attributeName == null)
            return null;
        NamedNodeMap attributes = node.getAttributes();
        Node attribute = attributes.getNamedItem(attributeName);
        String value = null;
        if (attribute != null)
            value = attribute.getNodeValue();
        return value;
    }

    /**
     * Builds an XML text node.
     * 
     * @param name
     * @param value
     * @return
     */
    public static String buildTextNode(String name, String value) {
        StringBuilder xml = new StringBuilder();
        xml.append(openTag(name));
        xml.append(getXMLText(value));
        xml.append(closeTag(name));
        return xml.toString();
    }

    /**
     * Returns an XML fragment for opening the given tag name.
     * 
     * @param tag
     * @return
     */
    public static String openTag(String tag) {
        StringBuilder xml = new StringBuilder();
        xml.append("<");
        xml.append(tag);
        xml.append(">");
        return xml.toString();
    }

    /**
     * Returns an XML fragment for closing the given tag name.
     * 
     * @param tag
     * @return
     */
    public static String closeTag(String tag) {
        StringBuilder xml = new StringBuilder();
        xml.append("</");
        xml.append(tag);
        xml.append(">");
        return xml.toString();
    }

    public static String getXMLText(String str) {
        str = StringUtils.unNull(str);
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("<", "&lt;");
        return str;
    }
}