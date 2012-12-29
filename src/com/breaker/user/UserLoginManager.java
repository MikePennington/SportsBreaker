package com.breaker.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.db.DBManager;
import com.breaker.security.PasswordEncryption;
import com.breaker.utils.RequestUtils;

public class UserLoginManager {

    private String email;
    private String password;

    public UserLoginManager(String email, String password, HttpServletRequest request) {
        this.email = email;
        this.password = password;
    }

    public boolean loginUser(HttpServletRequest request, HttpServletResponse response, boolean permanentLogin) {
        boolean loginSuccessful = false;
        User user = null;
        try {
            user = LoginUtils.getUserFromDB(email, PasswordEncryption.encrypt(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user != null) {
            loginSuccessful = true;
            user.setSoftLoggedIn(true);
            user.setHardLoggedIn(true);
            RequestUtils.setSessionAttribute(request, LoginUtils.USER_SESSION_NAME, user);
            if (permanentLogin)
                permanentLogin(response, user.getUserId());
        }
        return loginSuccessful;
    }

    private boolean permanentLogin(HttpServletResponse response, long userId) {
        if (response == null) {
            return false;
        } else {
            Random random = new Random();
            String securityString = String.valueOf(random.nextLong());
            String cookieValue = (new StringBuilder(String.valueOf(userId))).append(";").append(securityString)
                    .toString();
            Cookie loginCookie = new Cookie(LoginUtils.SOFT_LOGIN_COOKIE_NAME, cookieValue);
            // Keep user logged in for one month
            loginCookie.setMaxAge(60 * 60 * 24 * 30);
            loginCookie.setPath("/");
            response.addCookie(loginCookie);
            return saveCookieValueToDB(userId, securityString);
        }
    }

    private boolean saveCookieValueToDB(long userId, String securityString) {
        Connection con = null;
        PreparedStatement stmt = null;
        StringBuilder sql = new StringBuilder();
        boolean saved = false;
        try {
            con = DBManager.getDBConnection();
            sql.append("delete from user_login where user_id = ? ");
            stmt = con.prepareStatement(sql.toString());
            stmt.setLong(1, userId);
            stmt.execute();
            sql.setLength(0);
            sql.append("insert into user_login (user_id, security_string, created_date) ");
            sql.append("values (?,?,NOW())");
            stmt = con.prepareStatement(sql.toString());
            stmt.setLong(1, userId);
            stmt.setString(2, securityString);
            DBManager.log(sql.toString());
            saved = stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection(con, stmt, null);
        }

        return saved;
    }
}