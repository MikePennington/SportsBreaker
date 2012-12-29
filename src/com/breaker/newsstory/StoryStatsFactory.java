package com.breaker.newsstory;

import static com.breaker.utils.Constants.STAT_TYPE_COMMENT;
import static com.breaker.utils.Constants.STAT_TYPE_THUMBS_DOWN;
import static com.breaker.utils.Constants.STAT_TYPE_THUMBS_UP;
import static com.breaker.utils.Constants.STAT_TYPE_VIEW_DETAIL;
import static com.breaker.utils.Constants.STAT_TYPE_VISIT_URL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.breaker.db.DBManager;

public class StoryStatsFactory {
    private static Logger logger = Logger.getLogger("com/benchd/newsstory/StoryListFactory");

    public static StoryStatsBean getStatsBean(StoryBean story) {
        List<NewsStoryStat> allStats = getStatBeansByStoryId(story.getStoryId());

        List<NewsStoryStat> viewDetailList = getSpecificStatTypeList(allStats, STAT_TYPE_VIEW_DETAIL);
        List<NewsStoryStat> thumbsUpList = getSpecificStatTypeList(allStats, STAT_TYPE_THUMBS_UP);
        List<NewsStoryStat> thumbsDownList = getSpecificStatTypeList(allStats, STAT_TYPE_THUMBS_DOWN);
        List<NewsStoryStat> visitUrlList = getSpecificStatTypeList(allStats, STAT_TYPE_VISIT_URL);
        List<NewsStoryStat> commentsList = getSpecificStatTypeList(allStats, STAT_TYPE_COMMENT);

        int viewDetail = viewDetailList.size();
        int thumbsUp = thumbsUpList.size();
        int thumbsDown = thumbsDownList.size();
        int visitUrl = visitUrlList.size();

        StoryStatsBean statsBean = new StoryStatsBean();
        statsBean.setDetailViews(viewDetail);
        statsBean.setUrlViews(visitUrl);
        statsBean.setThumbsUp(thumbsUp);
        statsBean.setThumbsDown(thumbsDown);
        statsBean.setScore(thumbsUp - thumbsDown);

        double car = getCAR(story, thumbsUpList, thumbsDownList, viewDetailList, visitUrlList, commentsList);
        statsBean.setCurrentActivityRating(car);
        return statsBean;
    }

    /**
     * Saves a stat to the database and takes care of deleting any duplicate
     * stats for this user.
     * 
     * @param storyId
     * @param userId
     *                Values less than 0 indicate the user is not logged in and
     *                we are using their IP address
     * @param statId
     * @return
     */
    public static boolean saveStat(int storyId, long userId, int statId) {
        if (storyId < 1)
            return false;

        if (statId == STAT_TYPE_THUMBS_UP || statId == STAT_TYPE_THUMBS_DOWN) {
            deleteStat(storyId, userId, STAT_TYPE_THUMBS_UP);
            deleteStat(storyId, userId, STAT_TYPE_THUMBS_DOWN);
        } else if (statId == STAT_TYPE_VISIT_URL || statId == STAT_TYPE_VIEW_DETAIL) {
            // do nothing, these stats can happen multiple times
        } else if (statId == STAT_TYPE_COMMENT) {
            deleteStat(storyId, userId, statId);
        } else {
            return false;
        }

        Connection con = null;
        PreparedStatement stmt = null;
        String sql = "insert into news_story_stats (story_id, stat_type_id, value, user_id, action_date) value(?,?,?,?, NOW())";
        logger.debug("SQL: " + sql);

        try {
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, storyId);
            stmt.setInt(2, statId);
            stmt.setInt(3, 1);
            stmt.setLong(4, userId);
            DBManager.log(sql);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBManager.closeConnection(con, stmt, null);
        }
        return true;
    }

    /**
     * Returns the stat id of the given user's vote. Returns 0 if the given user
     * has not voted on the given storyId.
     * 
     * @param userId
     * @param storyId
     */
    public static int getUserVote(int storyId, long userId) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select stat_type_id from news_story_stats where story_id=? and user_id=? and (stat_type_id=? or stat_type_id=?)";
        logger.debug("SQL: " + sql);

        int vote = 0;
        try {
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, storyId);
            stmt.setLong(2, userId);
            stmt.setInt(3, STAT_TYPE_THUMBS_UP);
            stmt.setInt(4, STAT_TYPE_THUMBS_DOWN);
            DBManager.log(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                vote = rs.getInt("stat_type_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }
        return vote;
    }

    /**
     * Deletes a stat from the database.
     * 
     * @param storyId
     * @param userId
     * @param statId
     * @return
     */
    private static boolean deleteStat(int storyId, long userId, int statId) {
        Connection con = null;
        PreparedStatement stmt = null;
        String sql = "delete from news_story_stats where story_id=? and stat_type_id=? and user_id=?";
        logger.debug("SQL: " + sql);

        try {
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, storyId);
            stmt.setInt(2, statId);
            stmt.setLong(3, userId);
            DBManager.log(sql);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBManager.closeConnection(con, stmt, null);
        }
        return true;
    }

    private static List<NewsStoryStat> getStatBeansByStoryId(int storyId) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select * from news_story_stats where story_id=" + storyId;
        logger.debug("SQL: " + sql);

        List<NewsStoryStat> stats = new ArrayList<NewsStoryStat>();
        try {
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql);
            DBManager.log(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                NewsStoryStat stat = new NewsStoryStat();
                stat.setStatTypeId(rs.getInt("stat_type_id"));
                stat.setStoryId(rs.getInt("story_id"));
                stat.setUserId(rs.getInt("user_id"));
                stat.setValue(rs.getInt("value"));
                stat.setActionDate(new Date(rs.getTimestamp("action_date").getTime()));
                stats.add(stat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }
        return stats;
    }

    private static List<NewsStoryStat> getSpecificStatTypeList(List<NewsStoryStat> allStats, int statTypeId) {
        List<NewsStoryStat> specificStats = new ArrayList<NewsStoryStat>();
        for (NewsStoryStat stat : allStats) {
            if (stat.getStatTypeId() == statTypeId)
                specificStats.add(stat);
        }
        return specificStats;
    }

    private static double getCAR(StoryBean story, List<NewsStoryStat> thumbsUpList, List<NewsStoryStat> thumbsDownList,
            List<NewsStoryStat> viewList, List<NewsStoryStat> clickList, List<NewsStoryStat> comments) {

        CARCalculator carCalculator = new CARCalculator(story);
        carCalculator.addStatList(thumbsUpList);
        carCalculator.addStatList(thumbsDownList);
        carCalculator.addStatList(viewList);
        carCalculator.addStatList(clickList);
        carCalculator.addStatList(comments);

        return carCalculator.calculateCAR();
    }
}
