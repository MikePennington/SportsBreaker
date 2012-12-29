package com.breaker.jobs;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.breaker.db.DBManager;
import com.breaker.rss.Channel;
import com.breaker.rss.Image;
import com.breaker.rss.Item;
import com.breaker.rss.reader.FeedFactory;
import com.breaker.rss.reader.RSSFeed;

/**
 * Updates the feeds based on the rss_channels in the database.
 * 
 * @author Mike Pennington
 */
public class UpdateFeeds {

    private static Logger       logger                = Logger.getLogger(UpdateFeeds.class);
    private static final String PASSWORD              = "test123";

    private StringBuilder       output                = new StringBuilder();
    private int                 numberOfItemsInserted = 0;
    private int                 numberOfItemsUpdated  = 0;

    public static void main(String args[]) {
        UpdateFeeds update = new UpdateFeeds();
        update.runUpdate(null);
    }

    public static void updateFeedsFromJSP(HttpServletRequest request, HttpServletResponse response) {
        String password = request.getParameter("password");
        if (PASSWORD.equals(password)) {
            UpdateFeeds update = new UpdateFeeds();
            update.runUpdate(response);
        }
    }

    public void runUpdate(HttpServletResponse response) {
        try {
            updateFeeds();
            if (response != null) {
                PrintWriter writer = response.getWriter();
                writer.println(output.toString());
                writer.println("numberOfItemsInserted = " + numberOfItemsInserted);
                writer.println("numberOfItemsUpdated = " + numberOfItemsUpdated);
            }
        } catch (Exception e) {
            // do nothing
        }
    }

    public void updateFeeds() {

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        int numChannels = 0;
        try {
            con = DBManager.getDBConnection();
            stmt = con.createStatement();
            String sql = "select count(*) from rss_channels";
            DBManager.log(sql.toString());
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                numChannels = rs.getInt(1);
            }
        } catch (Exception e) {
            logger.warn("Exception", e);
            e.printStackTrace();
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }

        int GROUP_SIZE = 20;
        for (int pos = 0; pos < numChannels; pos = pos + GROUP_SIZE) {
            try {
                System.out.println("******* Channel = " + pos);
                if (true) {
                    Runtime runtime = Runtime.getRuntime();
                    long maxMemory = runtime.maxMemory();
                    long allocatedMemory = runtime.totalMemory();
                    long freeMemory = runtime.freeMemory();
                    System.out.println("free memory: " + freeMemory / 1024);
                    System.out.println("allocated memory: " + allocatedMemory / 1024);
                    System.out.println("max memory: " + maxMemory / 1024);
                    System.out.println("total free memory: " + (freeMemory + (maxMemory - allocatedMemory)) / 1024);
                }

                con = DBManager.getDBConnection();
                stmt = con.createStatement();
                String sql = "select channel_id, link from rss_channels LIMIT " + pos + "," + GROUP_SIZE;
                DBManager.log(sql.toString());
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int channelId = rs.getInt("channel_id");
                    String link = rs.getString("link");
                    output.append("\nFetching feed: " + link);
                    RSSFeed feed = FeedFactory.getFeed(link);
                    if (feed == null)
                        continue;

                    Channel channel = feed.getChannel();
                    if (channel == null)
                        continue;

                    // Save or update the information for the rss channel
                    updateChannel(channel, channelId);

                    // Save/update the individual items for this channel
                    List<Item> items = channel.getItems();
                    for (int j = 0; items != null && j < items.size(); j++)
                        saveItem(items.get(j), channelId);
                }

            } catch (Exception e) {
                logger.warn("Exception", e);
                e.printStackTrace();
            } finally {
                DBManager.closeConnection(con, stmt, rs);
                con = null;
                stmt = null;
                rs = null;
            }
        }

        return;
    }

    /**
     * Updates the meta-information about a particular rss_channel.
     * 
     * @param channel
     * @param channelId
     */
    private void updateChannel(Channel channel, int channelId) {
        if (channel == null)
            return;

        Connection con = null;
        PreparedStatement stmt = null;
        StringBuilder sql = new StringBuilder();
        int pi = 1;

        try {
            sql.append("update rss_channels ");
            sql.append("set title=?, description=?, webpage_link=?, language=?, copyright=? ");
            sql.append("where channel_id=?");
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql.toString());
            stmt.setString(pi++, channel.getTitle());
            stmt.setString(pi++, channel.getDescription());
            stmt.setString(pi++, channel.getLink());
            stmt.setString(pi++, channel.getLanguage());
            stmt.setString(pi++, channel.getCopyright());
            stmt.setInt(pi++, channelId);

            DBManager.log(sql.toString());
            stmt.execute();
        } catch (SQLException e) {
            logger.warn("SQLException", e);
        } finally {
            DBManager.closeConnection(con, stmt, null);
        }

        updateImage(channel.getImage(), channelId);
        return;
    }

    /**
     * Updates the image information for a particular rss channel.
     * 
     * @param image
     * @param channelId
     */
    private void updateImage(Image image, int channelId) {
        if (image == null)
            return;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder("select image_id from rss_channel_images where channel_id=?");

        try {
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql.toString());
            stmt.setInt(1, channelId);

            DBManager.log(sql.toString());
            rs = stmt.executeQuery();
            if (rs.next()) {
                sql.setLength(0);
                sql.append("update rss_channel_images ");
                sql.append("set url=?, link=?, width=?, height=? ");
                sql.append("where channel_id=?");
                stmt.close();
                stmt = null;
                stmt = con.prepareStatement(sql.toString());
                int pi = 1;
                stmt.setString(pi++, image.getUrl());
                stmt.setString(pi++, image.getLink());
                stmt.setInt(pi++, image.getWidth());
                stmt.setInt(pi++, image.getHeight());
                stmt.setInt(pi++, channelId);

                DBManager.log(sql.toString());
                stmt.execute();
            } else {
                sql.setLength(0);
                sql.append("insert into rss_channel_images ");
                sql.append("(url, link, width, height, channel_id) ");
                sql.append("values (?, ?, ?, ?, ?)");
                stmt.close();
                stmt = null;
                stmt = con.prepareStatement(sql.toString());
                int pi = 1;
                stmt.setString(pi++, image.getUrl());
                stmt.setString(pi++, image.getLink());
                stmt.setInt(pi++, image.getWidth());
                stmt.setInt(pi++, image.getHeight());
                stmt.setInt(pi++, channelId);

                DBManager.log(sql.toString());
                stmt.execute();
            }
        } catch (SQLException e) {
            logger.warn("SQLException", e);
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Inserts a new item into rss_items, or updates the information for an item
     * that is already inserted.
     * 
     * @param item
     * @param channelId
     */
    private void saveItem(Item item, int channelId) {
        if (item == null)
            return;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder sql2 = new StringBuilder();
        int pi = 1;
        int itemId = 0;

        try {
            con = DBManager.getDBConnection();
            String sql1 = "select item_id, published_date from rss_items where guid=?";
            stmt = con.prepareStatement(sql1);
            stmt.setString(pi++, item.getGuid());
            DBManager.log(sql1.toString());
            rs = stmt.executeQuery();
            if (rs.next()) {

                // To speed up the process, we only update if the published date
                // is unknown or has changed
                Date oldPublishedDate = new Date(rs.getTimestamp("published_date").getTime());
                if (item.getPublishedDate() == null || item.getPublishedDate().after(oldPublishedDate)) {

                    itemId = rs.getInt("item_id");

                    sql2.append("update rss_items ");
                    sql2.append("set title=?, link=?, description=?, ");
                    sql2.append("published_date=? ");
                    sql2.append("last_updated=? ");
                    sql2.append("where item_id=?");
                    stmt.close();
                    stmt = null;
                    stmt = con.prepareStatement(sql2.toString());
                    pi = 1;
                    stmt.setString(pi++, item.getTitle());
                    stmt.setString(pi++, item.getLink());
                    stmt.setString(pi++, item.getDescription());
                    java.util.Date publishedDate = item.getPublishedDate();
                    if (publishedDate != null)
                        stmt.setTimestamp(pi++, new Timestamp(item.getPublishedDate().getTime()), Calendar
                                .getInstance());
                    else
                        stmt.setTimestamp(pi++, new Timestamp(0L));

                    // Set last updated time to right now
                    stmt.setTimestamp(pi++, new Timestamp(new Date().getTime()));
                    stmt.setInt(pi++, itemId);

                    DBManager.log(sql2.toString());
                    stmt.execute();

                    // Increment number of rss items updated
                    numberOfItemsUpdated++;
                }
            } else {
                sql2.append("insert into rss_items ");
                sql2.append("(guid, title, link, description, last_updated, published_date) ");
                sql2.append("values (?,?,?,?,?,?)");
                stmt.close();
                stmt = null;
                stmt = con.prepareStatement(sql2.toString());
                pi = 1;
                stmt.setString(pi++, item.getGuid());
                stmt.setString(pi++, item.getTitle());
                stmt.setString(pi++, item.getLink());
                stmt.setString(pi++, item.getDescription());

                // Set last updated time to right now
                stmt.setTimestamp(pi++, new Timestamp(new Date().getTime()));

                java.util.Date publishedDate = item.getPublishedDate();
                if (publishedDate != null) {
                    Timestamp timestamp = new Timestamp(item.getPublishedDate().getTime());
                    stmt.setTimestamp(pi++, timestamp, Calendar.getInstance());
                } else {
                    stmt.setTimestamp(pi++, new Timestamp(0L));
                }

                DBManager.log(sql2.toString());
                stmt.execute();

                // Go back and get the item id
                sql2.setLength(0);
                sql2.append("select item_id from rss_items where guid=?");
                stmt.close();
                stmt = null;
                stmt = con.prepareStatement(sql2.toString());
                pi = 1;
                stmt.setString(pi++, item.getGuid());

                DBManager.log(sql2.toString());
                rs = stmt.executeQuery();
                if (rs.next())
                    itemId = rs.getInt("item_id");

                // Increment the numberof items that have been inserted
                numberOfItemsInserted++;
            }
            updateChannelItem(channelId, itemId);
        } catch (SQLException e) {
            logger.warn("SQLException", e);
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Add or update the associations between rss_items and the channel they
     * came from.
     * 
     * @param channelId
     * @param itemId
     */
    private void updateChannelItem(int channelId, int itemId) {
        if (channelId <= 0 || itemId <= 0)
            return;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        int pi = 1;

        try {
            con = DBManager.getDBConnection();
            sql.append("select * from rss_channel_items where channel_id=? and item_id=?");
            stmt = con.prepareStatement(sql.toString());
            stmt.setInt(pi++, channelId);
            stmt.setInt(pi++, itemId);

            DBManager.log(sql.toString());
            rs = stmt.executeQuery();
            if (!rs.next()) {
                sql.setLength(0);
                sql.append("insert into rss_channel_items ");
                sql.append("(channel_id, item_id) ");
                sql.append("values (?,?)");
                stmt.close();
                stmt = null;
                stmt = con.prepareStatement(sql.toString());
                pi = 1;
                stmt.setInt(pi++, channelId);
                stmt.setInt(pi++, itemId);

                DBManager.log(sql.toString());
                stmt.execute();
            }
        } catch (SQLException e) {
            logger.warn("SQLException", e);
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }
    }
}