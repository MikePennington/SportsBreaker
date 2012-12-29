package com.breaker.newsstory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.breaker.db.DBManager;
import com.breaker.db.SQLBuilder;
import com.breaker.rss.FeedBroker;
import com.breaker.team.TeamDBUtils;
import com.breaker.user.User;
import com.breaker.utils.Constants;

public class StoryListFactory {
    private static Logger logger = Logger.getLogger("com/benchd/newsstory/StoryListFactory");

    public static int getStoryListCount(StoryListParameters parameters) {
        if (parameters == null)
            parameters = new StoryListParameters();
        String sql = getSQLString(parameters, true);
        int count = 0;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql);
            DBManager.log(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }
        return count;
    }

    /**
     * Returns a List of StoryBean's based upon the given parameters
     * 
     * @param parameters
     * @return
     */
    public static List<StoryBean> getStoryList(StoryListParameters parameters) {
        List<StoryBean> stories = new ArrayList<StoryBean>();
        if (parameters == null)
            parameters = new StoryListParameters();
        String sql = getSQLString(parameters, false);
        logger.debug("SQL: " + sql);
        System.out.println("SQL: " + sql);

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql);
            DBManager.log(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                StoryBean bean = new StoryBean();
                bean.setStoryId(rs.getInt("id"));
                bean.setTitle(rs.getString("title"));
                bean.setDescription(rs.getString("description"));
                bean.setBrokenDate(rs.getTimestamp("broken_date"));
                bean.setUrl(rs.getString("url"));
                User user = new User();
                user.setUserId(rs.getInt("broken_by"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                bean.setBrokenByUser(user);

                StoryStatsBean stats = StoryStatsFactory.getStatsBean(bean);
                bean.setStats(stats);

                stories.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }

        if (parameters.getSortBy() == StoryListParameters.SORT_BY_RATING) {
            Collections.sort(stories, new StoryComparator(StoryComparator.COMPARE_SCORE));
        } else if (parameters.getSortBy() == StoryListParameters.SORT_BY_CAR) {
            Collections.sort(stories, new StoryComparator(StoryComparator.COMPARE_CAR));
        }

        // Only return the subset of stories to show on the page
        if (parameters.getNumStoriesToReturn() > -1) {
            List<StoryBean> storiesToReturn = new ArrayList<StoryBean>();
            int min = parameters.getStartPosition();
            int max = min + parameters.getNumStoriesToReturn();
            max = max > stories.size() ? stories.size() : max;
            for (int i = min; i < max; i++) {
                StoryBean temp = stories.get(i);
                storiesToReturn.add(temp);
            }
            stories = storiesToReturn;
        }

        populateRSSItems(stories);
        if (parameters.isPopulateCategories())
            populateCategories(stories);
        if (parameters.isPopulateTeams())
            populateTeams(stories);
        if (parameters.isCountComments())
            countComments(stories);

        return stories;
    }

    public static int countStoriesByUserId(long userId) {
        int count = 0;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select count(*) from news_stories where broken_by=" + userId;
        try {
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql);
            DBManager.log(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }
        return count;
    }

    /*
     * private static String getHQLString(StoryListParameters parameters) {
     * StringBuilder hql = new StringBuilder();
     * 
     * hql.append("from StoryBean as story"); // Criteria criteria =
     * session.createCriteria(StoryBean.class);
     * 
     * if (parameters.getCategoryId() > 0) { hql.append(" left outer join
     * story.categories as category with category.id=");
     * hql.append(parameters.getCategoryId()); //
     * criteria.add(Restrictions.eq("categories.id", //
     * parameters.getCategoryId())); }
     * 
     * if (parameters.getTeamId() > 0) { hql.append(" left outer join
     * story.teams as team with team.teamId=");
     * hql.append(parameters.getTeamId()); //
     * criteria.add(Restrictions.eq("teams.teamId", // parameters.getTeamId())); }
     * 
     * if (parameters.getBrokenByUserId() > 0) { hql.append(" where brokenById =
     * "); hql.append(parameters.getBrokenByUserId()); //
     * criteria.add(Restrictions.eq("brokenById", //
     * parameters.getBrokenByUserId())); }
     * 
     * if (parameters.getSortBy() == StoryListParameters.SORT_BY_RATING) {
     * hql.append(" order by story.brokenDate desc"); //
     * criteria.addOrder(Order.desc("brokenDate")); } else { hql.append(" order
     * by story.brokenDate desc"); //
     * criteria.addOrder(Order.desc("brokenDate")); }
     * 
     * return hql.toString(); }
     */

    private static String getSQLString(StoryListParameters parameters, boolean onlyCount) {
        SQLBuilder sqlBuilder = new SQLBuilder();

        if (onlyCount) {
            sqlBuilder.appendColumn("count(*)");
        } else {
            sqlBuilder.appendColumn("ns.id");
            sqlBuilder.appendColumn("ns.title");
            sqlBuilder.appendColumn("ns.description");
            sqlBuilder.appendColumn("ns.broken_by");
            sqlBuilder.appendColumn("ns.broken_date");
            sqlBuilder.appendColumn("ns.url");
            sqlBuilder.appendColumn("u.username");
            sqlBuilder.appendColumn("u.email");
        }

        sqlBuilder.appendFrom("news_stories ns");

        if (!onlyCount) {
            sqlBuilder.appendFrom("users u");
            sqlBuilder.appendWhere("ns.broken_by=u.user_id");
        }

        if (parameters.getCategoryId() > 1) {
            if (!onlyCount)
                sqlBuilder.appendColumn("nsc.category_id");
            sqlBuilder.appendFrom("news_story_categories nsc");
            sqlBuilder.appendWhere("nsc.category_id=" + parameters.getCategoryId());
            sqlBuilder.appendWhere("ns.id=nsc.news_story_id");
        }

        if (parameters.getTeamId() > 0) {
            if (!onlyCount)
                sqlBuilder.appendColumn("nst.team_id");
            sqlBuilder.appendFrom("news_story_teams nst");
            sqlBuilder.appendWhere("nst.team_id=" + parameters.getTeamId());
            sqlBuilder.appendWhere("ns.id=nst.news_story_id");
        }

        if (parameters.getBrokenByUserId() > 0) {
            sqlBuilder.appendWhere("ns.broken_by=" + parameters.getBrokenByUserId());
        }

        if (parameters.getUserIdVotedOn() > 0) {
            sqlBuilder.appendFrom("news_story_stats nss");
            sqlBuilder.appendWhere("ns.id=nss.story_id");
            sqlBuilder.appendWhere("nss.user_id=" + parameters.getUserIdVotedOn());
            sqlBuilder.appendWhere("nss.stat_type_id in (" + Constants.STAT_TYPE_THUMBS_DOWN + ","
                    + Constants.STAT_TYPE_THUMBS_UP + ")");
        } else if (parameters.getUserIdThumbsUp() > 0) {
            sqlBuilder.appendFrom("news_story_stats nss");
            sqlBuilder.appendWhere("ns.id=nss.story_id");
            sqlBuilder.appendWhere("nss.user_id=" + parameters.getUserIdThumbsUp());
            sqlBuilder.appendWhere("nss.stat_type_id=" + Constants.STAT_TYPE_THUMBS_UP);
        } else if (parameters.getUserIdThumbsDown() > 0) {
            sqlBuilder.appendFrom("news_story_stats nss");
            sqlBuilder.appendWhere("ns.id=nss.story_id");
            sqlBuilder.appendWhere("nss.user_id=" + parameters.getUserIdThumbsDown());
            sqlBuilder.appendWhere("nss.stat_type_id=" + Constants.STAT_TYPE_THUMBS_DOWN);
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (parameters.getShowDates() == StoryListParameters.SHOW_DATES_24_HOURS) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR_OF_DAY, -24);
            sqlBuilder.appendWhere("ns.broken_date > '" + df.format(cal.getTime()) + "'");
        } else if (parameters.getShowDates() == StoryListParameters.SHOW_DATES_30_DAYS) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -30);
            sqlBuilder.appendWhere("ns.broken_date > '" + df.format(cal.getTime()) + "'");
        } else if (parameters.getShowDates() == StoryListParameters.SHOW_DATES_YEAR) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1);
            sqlBuilder.appendWhere("ns.broken_date > '" + df.format(cal.getTime()) + "'");
        }

        if (!onlyCount) {
            if (parameters.getSortBy() == StoryListParameters.SORT_BY_RATING)
                sqlBuilder.appendOrderBy("ns.broken_date desc");
            else
                sqlBuilder.appendOrderBy("ns.broken_date desc");

            /*
             * sqlBuilder.appendExtraAtEnd("limit ");
             * sqlBuilder.appendExtraAtEnd(String.valueOf(parameters.getStartPosition()));
             * sqlBuilder.appendExtraAtEnd(",");
             * sqlBuilder.appendExtraAtEnd(String.valueOf(parameters.getNumStoriesToReturn()));
             */
        }

        // Uncomment to make dev faster
        // sqlBuilder.appendExtraAtEnd("limit 0,5");

        return sqlBuilder.toString();
    }

    /**
     * Populates the categories associated with each story in the List.
     * 
     * @param stories
     */
    public static void populateCategories(List<StoryBean> stories) {
        if (stories == null)
            return;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // int start = parameters.getStartPosition();
        // int end = start + parameters.getNumStoriesToReturn();
        try {
            con = DBManager.getDBConnection();
            // for (int i = start; i < stories.size() && i < end; i++) {
            for (int i = 0; i < stories.size(); i++) {
                StoryBean story = stories.get(i);
                String sql = "select c.category_id, c.category_name from categories c, news_story_categories nsc where c.category_id = nsc.category_id and news_story_id = "
                        + story.getStoryId();
                try {
                    stmt = con.prepareStatement(sql);
                    DBManager.log(sql);
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        CategoryBean category = new CategoryBean();
                        category.setId(rs.getInt("category_id"));
                        category.setName(rs.getString("category_name"));
                        story.addCategory(category);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    DBManager.closeConnection(null, stmt, rs);
                }
            }
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Populates the categories associated with each story in the List.
     * 
     * @param stories
     */
    public static void populateTeams(List<StoryBean> stories) {
        if (stories == null)
            return;

        // int start = parameters.getStartPosition();
        // int end = start + parameters.getNumStoriesToReturn();
        // for (int i = start; i < stories.size() && i < end; i++) {
        for (int i = 0; i < stories.size(); i++) {
            StoryBean story = stories.get(i);
            story.setTeams(TeamDBUtils.getTeamListByStory(story.getStoryId()));
        }
    }

    /**
     * Populates the RSS items the stories in the list came from. The rssId must
     * already be set in the StoryBean in order to populate the RSS Item.
     * 
     * @param stories
     * @param parameters
     */
    public static void populateRSSItems(List<StoryBean> stories) {
        if (stories == null)
            return;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // int start = parameters.getStartPosition();
        // int end = start + parameters.getNumStoriesToReturn();
        // for (int i = start; i < stories.size() && i < end; i++) {
        for (int i = 0; i < stories.size(); i++) {
            StoryBean story = stories.get(i);
            String sql = "select item_id from rss_items where story_id=" + story.getStoryId();
            try {
                con = DBManager.getDBConnection();
                stmt = con.prepareStatement(sql);
                DBManager.log(sql);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    long rssId = rs.getLong("item_id");
                    if (rssId > 0)
                        story.setRssItem(FeedBroker.getFeedInfoItem(rssId));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBManager.closeConnection(con, stmt, rs);
            }
        }
    }

    private static void countComments(List<StoryBean> stories) {
        for (StoryBean story : stories) {
            story.setNumberOfComments(CommentsFactory.countComments(story.getStoryId()));
        }
    }
}