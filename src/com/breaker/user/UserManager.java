package com.breaker.user;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.breaker.utils.HibernateUtil;

public class UserManager {

    private static Logger logger = Logger.getLogger(UserManager.class);

    public static boolean createUser(User user) {
        boolean completed = false;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(user);
            completed = true;
        } catch (Exception e) {
            logger.error("Error", e);
        } finally {
            session.getTransaction().commit();
        }

        return completed;
    }

    public static boolean updateUser(User user) {
        boolean completed = false;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.update(user);
            completed = true;
        } catch (Exception e) {
            logger.error("Error", e);
        } finally {
            session.getTransaction().commit();
        }

        return completed;
    }

    public static User getUserFromDB(long userId) {
        Session session = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            user = (User) session.get(User.class, userId);
        } catch (Exception e) {
            logger.error("Error", e);
        } finally {
            session.getTransaction().commit();
        }

        return user;
    }

    public static User getUserByEmail(String email) {
        Session session = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            user = (User) session.createCriteria(User.class).add(Restrictions.eq("email", email))
                    .uniqueResult();
        } catch (Exception e) {
            logger.error("Error", e);
        } finally {
            session.getTransaction().commit();
        }

        return user;
    }

    public static User getUserByUsername(String username) {
        Session session = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            user = (User) session.createCriteria(User.class).add(
                    Restrictions.eq("userName", username));
        } catch (Exception e) {
            logger.error("Error", e);
        } finally {
            session.getTransaction().commit();
        }

        return user;
    }
}