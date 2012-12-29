package com.breaker.rss;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.breaker.db.DBManager;
import com.breaker.db.SQLBuilder;
import com.breaker.newsstory.StoryBean;
import com.breaker.newsstory.StoryFactory;
import com.breaker.utils.StringUtils;

public class FeedBroker {

    private static Logger    logger           = Logger.getLogger("com/benchd/webapp/IndexAction");

    private static final int NUM_BUFFER_FEEDS = 50;

    public static List<FeedItemInfo> getFeeds(FeedBrokerParameters params) {
        if (params == null)
            params = new FeedBrokerParameters();

        List<FeedItemInfo> rssItems = new ArrayList<FeedItemInfo>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = buildSQL(params);
        System.out.println("Feed sql: " + sql);
        try {
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql.toString());
            DBManager.log(sql.toString());
            rs = stmt.executeQuery();
            int position = -1;
            List<String> titles = new ArrayList<String>();
            while (rs.next() && titles.size() < params.getNumberOfFeedsToReturn()) {

                // Check for duplicates
                Date publishedDate = new java.util.Date(rs.getTimestamp("published_date").getTime());
                if (publishedDate == null || new Date().getTime() < publishedDate.getTime())
                    continue;
                String title = rs.getString("title");
                if (titles.contains(title))
                    continue;

                position++;
                if (position >= params.getStartPosition()) {
                    titles.add(title);
                    FeedItemInfo rssItem = parseResultSet(rs);
                    int storyId = rssItem.getStoryId();
                    if (params.isReturnBrokenById() && storyId > 0) {
                        StoryBean story = StoryFactory.getStoryById(storyId);
                        rssItem.setBrokenBy(story.getBrokenByUser().getUserId());
                    }
                    rssItems.add(rssItem);
                }
            }
        } catch (SQLException e) {
            logger.warn("SQLException: ", e);
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }

        return rssItems;
    }

    /**
     * Returns a specific rss Item by id
     * 
     * @param rssId
     * @return
     */
    public static FeedItemInfo getFeedInfoItem(long rssId) {
        FeedBrokerParameters params = new FeedBrokerParameters();
        params.setRssId(rssId);

        FeedItemInfo rssItem = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = buildSQL(params);
        try {
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql.toString());
            DBManager.log(sql.toString());
            rs = stmt.executeQuery();
            while (rs.next()) {
                rssItem = parseResultSet(rs);

                int storyId = rs.getInt(rssItem.getStoryId());
                if (params.isReturnBrokenById() && storyId > 0) {
                    StoryBean story = StoryFactory.getStoryById(storyId);
                    rssItem.setBrokenBy(story.getBrokenByUser().getUserId());
                }
            }
        } catch (SQLException e) {
            logger.warn("SQLException: ", e);
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }

        return rssItem;
    }

    private static FeedItemInfo parseResultSet(ResultSet rs) {
        FeedItemInfo rssItem = new FeedItemInfo();
        try {
            rssItem.setItemId(rs.getInt("item_id"));
            rssItem.setTitle(rs.getString("title"));
            rssItem.setLink(rs.getString("link"));
            rssItem.setDescription(rs.getString("description"));
            Date publishedDate = new java.util.Date(rs.getTimestamp("published_date").getTime());
            rssItem.setPubDate(StringUtils.formatDate(publishedDate));
            rssItem.setChannelId(rs.getInt("channel_id"));
            rssItem.setChannelTitle(rs.getString("channel_title"));
            rssItem.setChannelLink(rs.getString("channel_link"));
            rssItem.setChannelWebpageLink(rs.getString("channel_webpage_link"));
            rssItem.setCategoryId(rs.getInt("category_id"));
            rssItem.setStoryId(rs.getInt("story_id"));
        } catch (SQLException e) {
            logger.warn("SQLException: ", e);
        }
        return rssItem;
    }

    private static String buildSQL(FeedBrokerParameters params) {
        SQLBuilder sql = new SQLBuilder();

        sql.appendColumn("ri.item_id, ri.title, ri.guid, ri.link");
        sql.appendColumn("ri.description, ri.published_date, ri.story_id");
        sql.appendColumn("rc.channel_id, rc.category_id, rc.title as 'channel_title'");
        sql.appendColumn("rc.link as 'channel_link', rc.webpage_link as 'channel_webpage_link'");
        sql.appendFrom("rss_items ri, rss_channel_items rci, rss_channels rc");
        sql.appendWhere("rci.channel_id = rc.channel_id");
        sql.appendWhere("rci.item_id = ri.item_id");
        if (params.getRssId() > 0)
            sql.appendWhere("ri.item_id = " + params.getRssId());
        else if (params.getTeamId() > 0)
            sql.appendWhere("rc.team_id=" + params.getTeamId());
        else if (params.getCollegeId() > 0)
            sql.appendWhere("rc.college_id=" + params.getCollegeId());
        else if (params.getCategoryId() > 0)
            sql.appendWhere("rc.category_id=" + params.getCategoryId());
        sql.appendOrderBy("ri.published_date desc");
        sql.appendExtraAtEnd("LIMIT " + (FeedBrokerParameters.MAX_FEEDS_TO_RETURN + NUM_BUFFER_FEEDS));
        return sql.toString();
    }

}