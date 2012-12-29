package com.breaker.user;

import com.breaker.db.DBManager;
import com.breaker.utils.HibernateUtil;
import com.breaker.utils.RequestUtils;
import com.breaker.utils.StringUtils;

import java.sql.*;
import javax.servlet.http.*;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class LoginUtils {

    protected static String SOFT_LOGIN_COOKIE_NAME = "soft_login";
    protected static String USER_SESSION_NAME      = "user_object";

    public static boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response) {
        User user = getUserObject(request, response);

        boolean loggedIn = false;
        if (user != null)
            loggedIn = user.isHardLoggedIn() || user.isSoftLoggedIn();
        return loggedIn;
    }

    public static User getUserObject(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) RequestUtils.getSessionObject(request, USER_SESSION_NAME);
        if (user == null) {
            long userId = checkSoftLoggedIn(request, response);
            if (userId > 0L) {
                user = UserManager.getUserFromDB(userId);
                user.setSoftLoggedIn(true);
            }
            RequestUtils.setSessionAttribute(request, USER_SESSION_NAME, user);
        }
        return user;
    }

    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        RequestUtils.removeSessionObject(request, USER_SESSION_NAME);
        Cookie cookie = new Cookie(SOFT_LOGIN_COOKIE_NAME, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private static long checkSoftLoggedIn(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookies[] = request.getCookies();
        String cookieValue = null;
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (cookies[i] == null || !SOFT_LOGIN_COOKIE_NAME.equals(cookies[i].getName()))
                continue;
            cookieValue = cookies[i].getValue();
            break;
        }

        if (StringUtils.isEmpty(cookieValue))
            return 0L;

        String[] values = cookieValue.split(";");
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        long userId = 0L;
        try {
            sql.append("select created_date from user_login where user_id=? and security_string=?");
            con = DBManager.getDBConnection();
            stmt = con.prepareStatement(sql.toString());
            stmt.setLong(1, StringUtils.toLong(values[0]));
            stmt.setLong(2, StringUtils.toLong(values[1]));
            DBManager.log(sql.toString());
            rs = stmt.executeQuery();
            if (rs.next()) {
                userId = StringUtils.toLong(values[0]);
            } else if (response != null) {
                // Delete the cookie if it doesn't match whats in the database.
                Cookie c = new Cookie(SOFT_LOGIN_COOKIE_NAME, "");
                c.setMaxAge(0);
                response.addCookie(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection(con, stmt, rs);
        }

        return userId;
    }

    /**
     * Returns a User from the given email and password.
     * 
     * @param email
     * @param encryptedPassword
     * @return
     */
    public static User getUserFromDB(String email, String encryptedPassword) {
        Session session = null;
        User user = null;
        try {
            // password = PasswordEncryption.encrypt(password);
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            user = (User) session.createCriteria(User.class).add(Restrictions.eq("email", email))
                    .add(Restrictions.eq("encryptedPassword", encryptedPassword)).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.getTransaction().commit();
        }

        return user;
    }
}