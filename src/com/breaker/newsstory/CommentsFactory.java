package com.breaker.newsstory;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.breaker.utils.HibernateUtil;

public class CommentsFactory {

    public static int countComments(int storyId) {
        int numComments = 0;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(CommentBean.class).add(Restrictions.eq("storyId", storyId));
            numComments = criteria.list().size();
        } finally {
            if (session != null)
                session.getTransaction().commit();
        }
        return numComments;
    }

    public static List<CommentBean> getCommentsForStory(int storyId, boolean fetchUserObject) {
        List<CommentBean> comments = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(CommentBean.class).add(Restrictions.eq("storyId", storyId))
                    .addOrder(Order.asc("replyToId")).addOrder(Order.desc("commentDate"));
            if (fetchUserObject)
                criteria.setFetchMode("user", FetchMode.JOIN);
            comments = (List<CommentBean>) criteria.list();
        } finally {
            if (session != null)
                session.getTransaction().commit();
        }

        return comments;
    }

    /**
     * Inserts a new comment into the database.
     * 
     * @param bean
     * @return
     */
    public static boolean insertComment(CommentBean bean) {
        boolean successful = false;

        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(bean);
            successful = true;
        } finally {
            if (session != null)
                session.getTransaction().commit();
        }

        return successful;
    }
}
