package com.breaker.newsstory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.breaker.db.DBManager;
import com.breaker.db.SQL;
import com.breaker.team.TeamBean;
import com.breaker.utils.HibernateUtil;

public class StoryFactory {
    private static Logger      logger              = Logger
                                                           .getLogger("com/benchd/newsstory/StoryFactory");

    public static final String SQL_UPDATE_RSS_ITEM = "update rss_items set story_id = ? where link = ?";
    public static final String SQL_INSERT_CATEGORY = "insert into news_story_categories (news_story_id, category_id) values (?,?)";
    public static final String SQL_INSERT_TEAM     = "insert into news_story_teams (news_story_id, team_id) values (?,?)";

    public static StoryBean getStoryById(int storyId) {
        StoryBean storyBean = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            storyBean = (StoryBean) session.get(StoryBean.class, storyId);
        } finally {
            if (session != null)
                session.getTransaction().commit();
        }

        if (storyBean != null) {
            List<StoryBean> storyList = new ArrayList<StoryBean>();
            storyList.add(storyBean);
            StoryListFactory.populateCategories(storyList);
            StoryListFactory.populateTeams(storyList);
            StoryListFactory.populateRSSItems(storyList);
            storyBean = storyList.get(0);
        }
        return storyBean;
    }

    /**
     * Saves a StoryBean to the database as a new story. Also updates the
     * rss_item table if this story was broken from an rss feed.
     * 
     * @param story
     * @return
     */
    public static int saveNewStoryToDB(StoryBean story) {
        int storyId = 0;
        boolean completed = false;
        Connection con = null;
        PreparedStatement stmt = null;
        StringBuffer sql = new StringBuffer();
        try {
            sql.append("insert into news_stories ");
            sql.append("(title, description, broken_by, broken_date, url) ");
            sql.append((new StringBuilder("values (?, ?, ?, ")).append(SQL.DATETIME).append(", ?)")
                    .toString());
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql.toString(), 1);
            int pi = 1;
            stmt.setString(pi++, story.getTitle());
            stmt.setString(pi++, story.getDescription());
            stmt.setLong(pi++, story.getBrokenByUser().getUserId());
            stmt.setString(pi++, story.getUrl());
            DBManager.log(sql.toString());
            int rowsUpdated = stmt.executeUpdate();
            completed = rowsUpdated > 0;
            if (completed) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next())
                    storyId = rs.getInt(1);
                story.setStoryId(storyId);
                insertCategories(story);
                insertTeams(story);
            }
        } catch (Exception e) {
            logger.warn("SQLException: ", e);
        } finally {
            DBManager.closeConnection(con, stmt, null);
        }

        if (completed)
            updateRSSItem(story);
        return storyId;
    }

    private static boolean updateRSSItem(StoryBean story) {
        boolean completed = false;
        Connection con = null;
        PreparedStatement stmt = null;
        StringBuilder sql = new StringBuilder();

        try {
            sql.append(SQL_UPDATE_RSS_ITEM);
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql.toString());
            int pi = 1;
            stmt.setInt(pi++, story.getStoryId());
            stmt.setString(pi++, story.getUrl());
            DBManager.log(sql.toString());
            int rowsUpdated = stmt.executeUpdate();
            completed = rowsUpdated > 0;
        } catch (SQLException e) {
            logger.warn("SQLException: ", e);
            return false;
        } finally {
            DBManager.closeConnection(con, stmt, null);
        }

        return completed;
    }

    /**
     * Inserts the into the news_story_categories table
     * 
     * @param story
     * @return
     */
    private static boolean insertCategories(StoryBean story) {
        boolean completed = false;
        Connection con = null;
        PreparedStatement stmt = null;
        String sql = SQL_INSERT_CATEGORY;
        List<CategoryBean> categories = story.getCategories();

        try {
            con = DBManager.getDBConnection();
            for (int i = 0; categories != null && i < categories.size(); i++) {
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, story.getStoryId());
                stmt.setInt(2, ((CategoryBean) categories.get(i)).getId());
                DBManager.log(sql);
                int rowsUpdated = stmt.executeUpdate();
                completed = rowsUpdated > 0;
            }
        } catch (SQLException e) {
            logger.warn("SQLException: ", e);
            return false;
        } finally {
            DBManager.closeConnection(con, stmt, null);
        }

        return completed;
    }

    /**
     * Inserts the into the news_story_teams table
     * 
     * @param story
     * @return
     */
    private static boolean insertTeams(StoryBean story) {
        boolean completed = false;
        Connection con = null;
        PreparedStatement stmt = null;
        String sql = SQL_INSERT_TEAM;
        List<TeamBean> teams = story.getTeams();

        try {
            con = DBManager.getDBConnection();
            for (int i = 0; teams != null && i < teams.size(); i++) {
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, story.getStoryId());
                stmt.setInt(2, ((TeamBean) teams.get(i)).getTeamId());
                DBManager.log(sql);
                int rowsUpdated = stmt.executeUpdate();
                completed = rowsUpdated > 0;
            }
        } catch (SQLException e) {
            logger.warn("SQLException: ", e);
            return false;
        } finally {
            DBManager.closeConnection(con, stmt, null);
        }

        return completed;
    }

    public static StoryBean findByURL(String url) {
        StoryBean storyBean = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            storyBean = (StoryBean) session.createCriteria(StoryBean.class).add(
                    Restrictions.eq("url", url)).uniqueResult();
        } finally {
            if (session != null)
                session.getTransaction().commit();
        }
        return storyBean;
    }
}
