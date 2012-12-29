package com.breaker.team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.breaker.db.SQLBuilder;
import com.breaker.utils.HibernateUtil;
import com.breaker.utils.StringUtils;

public class TeamDBUtils {
    private static Logger logger = Logger.getLogger("com/benchd/newsstory/TeamDBUtils");

    public static TeamBean getTeamById(int teamId) {
        TeamBean teamBean = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            teamBean = (TeamBean) session.get(TeamBean.class, teamId);
        } finally {
            if (session != null)
                session.getTransaction().commit();
        }
        return teamBean;
    }

    public static List<TeamBean> listTeamsByIds(Collection<Integer> teamIds) {
        List<TeamBean> teamBeans = new ArrayList<TeamBean>();
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            teamBeans = (List<TeamBean>) session.createCriteria(TeamBean.class).add(
                    Restrictions.in("teamId", teamIds)).list();
        } finally {
            if (session != null)
                session.getTransaction().commit();
        }
        return teamBeans;
    }

    public static List<TeamBean> getTeamListByCategory(int categoryId) {
        List<TeamBean> teams = new ArrayList<TeamBean>();
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            List<TeamBean> result = session.createCriteria(TeamBean.class).add(
                    Restrictions.eq("categoryId", categoryId)).addOrder(Order.asc("location"))
                    .addOrder(Order.asc("teamName")).list();
            ListIterator<TeamBean> iterator = result.listIterator();
            while (iterator.hasNext()) {
                TeamBean bean = iterator.next();
                teams.add(bean);
            }
        } finally {
            session.getTransaction().commit();
        }
        return teams;
    }

    public static List<TeamBean> listByCategoryAndConference(int categoryId, String conference,
            String subconference) {
        List<TeamBean> teams = new ArrayList<TeamBean>();
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            Criteria criteria = session.createCriteria(TeamBean.class).add(
                    Restrictions.eq("categoryId", categoryId));
            if (!StringUtils.isEmpty(conference))
                criteria.add(Restrictions.eq("conference", conference));
            if (!StringUtils.isEmpty(subconference))
                criteria.add(Restrictions.eq("subconference", subconference));
            teams = criteria.list();
        } finally {
            session.getTransaction().commit();
        }
        return teams;
    }

    /**
     * Returns a List of TeamBeans associated with the given storyId.
     * 
     * @param storyId
     * @return
     */
    public static List<TeamBean> getTeamListByStory(int storyId) {
        SQLBuilder sql = new SQLBuilder();
        sql.appendColumn("t.*");
        sql.appendFrom("teams t, news_story_teams nst ");
        sql.appendWhere("t.id = nst.team_id and nst.news_story_id=" + storyId);
        sql.appendOrderBy("location asc, name asc");

        logger.debug("SQL: " + sql);

        List<TeamBean> teams = new ArrayList<TeamBean>();

        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            List<TeamBean> result = session.createSQLQuery(sql.toString())
                    .addEntity(TeamBean.class).list();
            ListIterator<TeamBean> iterator = result.listIterator();
            while (iterator.hasNext()) {
                TeamBean bean = iterator.next();
                teams.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.getTransaction().commit();
        }

        return teams;
    }
}
