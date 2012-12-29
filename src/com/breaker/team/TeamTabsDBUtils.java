package com.breaker.team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.breaker.db.DBManager;
import com.breaker.utils.HibernateUtil;
import com.breaker.utils.StringUtils;

public class TeamTabsDBUtils {
    private static Logger logger = Logger.getLogger("com/benchd/newsstory/TeamTabsDBUtils");

    /**
     * Returns a List of TeamTabBeans associated with the given userId's
     * profile. This returns the given userId's "team tab" list.
     * 
     * @param userId
     * @return
     */
    public static List<TeamTabBean> getTeamListByUser(long userId) {
        Session session = null;
        List<TeamTabBean> teams = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            teams = session.createCriteria(TeamTabBean.class)
                    .add(Restrictions.eq("userId", userId)).addOrder(Order.asc("sortOrder")).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.getTransaction().commit();
        }
        return teams;
    }

    /**
     * Saves or updates the given TeamTabBean
     * 
     * @param teamTab
     * @return
     */
    public static List<TeamTabBean> saveOrUpdate(TeamTabBean teamTab) {
        Session session = null;
        List<TeamTabBean> teams = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.saveOrUpdate(teamTab);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.getTransaction().commit();
        }
        return teams;
    }

    public static TeamTabBean find(long userId, int teamId) {
        Session session = null;
        TeamTabBean teamTab = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            teamTab = (TeamTabBean) session.createCriteria(TeamTabBean.class).add(
                    Restrictions.eq("userId", userId)).add(Restrictions.eq("teamId", teamId))
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.getTransaction().commit();
        }
        return teamTab;
    }

    public static boolean insertNewList(long userId, List<TeamTabBean> teams) {
        if (teams == null)
            return false;

        deleteUserList(userId);

        ListIterator<TeamTabBean> iterator = teams.listIterator();
        for (int i = 0; i < teams.size(); i++) {
            insertNewTeam(iterator.next());
        }
        return true;
    }

    public static boolean deleteUserList(long userId) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "delete from user_teams where user_id=?";

        logger.debug("SQL: " + sql);

        try {
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql);
            stmt.setLong(1, userId);
            DBManager.log(sql);
            int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }
    }

    public static boolean insertNewTeam(long userId, int teamId, String name) {
        List<TeamTabBean> teams = TeamTabsDBUtils.getTeamListByUser(userId);
        if (teams == null)
            teams = new ArrayList<TeamTabBean>();
        TeamTabBean tabBean = new TeamTabBean();
        if (StringUtils.isEmpty(name)) {
            TeamBean team = TeamDBUtils.getTeamById(teamId);
            if (team != null) {
                name = team.getDisplayName();
            }
        }
        tabBean.setName(name);
        tabBean.setUserId(userId);
        tabBean.setTeamId(teamId);
        tabBean.setSortOrder(teams.size() + 1);
        return insertNewTeam(tabBean);
    }

    public static boolean insertNewTeam(TeamTabBean tabBean) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "insert into user_teams (user_id, team_id, sort_order, name) values (?,?,?,?)";

        logger.debug("SQL: " + sql);

        try {
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql);
            stmt.setLong(1, tabBean.getUserId());
            stmt.setInt(2, tabBean.getTeamId());
            stmt.setInt(3, tabBean.getSortOrder());
            stmt.setString(4, tabBean.getName());
            DBManager.log(sql);
            int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }
    }

    public static void moveTeam(long userId, int teamId, int numPositions) {
        List<TeamTabBean> teams = TeamTabsDBUtils.getTeamListByUser(userId);
        for (int i = 0; teams != null && i < teams.size(); i++) {
            TeamTabBean team = teams.get(i);
            if (team.getTeamId() == teamId && i >= (numPositions * -1)) {
                if (numPositions > 0) {
                    teams.add(i + numPositions + 1, team);
                    teams.remove(i);
                } else if (numPositions < 0) {
                    teams.add(i + numPositions, team);
                    teams.remove(i + 1);
                }
                break;
            }
        }

        // After reordering, change sortOrder to the order they appear in the
        // list
        for (int i = 0; teams != null && i < teams.size(); i++) {
            teams.get(i).setSortOrder(i + 1);
        }

        insertNewList(userId, teams);
    }

    public static boolean removeTeam(long userId, int teamId) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "delete from user_teams where user_id=? and team_id=?";

        logger.debug("SQL: " + sql);

        try {
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql);
            stmt.setLong(1, userId);
            stmt.setInt(2, teamId);
            DBManager.log(sql);
            int updated = stmt.executeUpdate();
            cleanUpOrdering(userId);
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }
    }

    private static void cleanUpOrdering(long userId) {
        List<TeamTabBean> teamTabs = TeamTabsDBUtils.getTeamListByUser(userId);
        deleteUserList(userId);
        for (int i = 0; teamTabs != null && i < teamTabs.size(); i++)
            insertNewTeam(userId, teamTabs.get(i).getTeamId(), teamTabs.get(i).getName());
    }
}
