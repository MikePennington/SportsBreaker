package com.breaker.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.connection.ConnectionProvider;

/**
 * This class acts as an interface into the connection pooler. All database
 * connections should be made through this class.
 * 
 * @author Mike
 */
public class DBManager implements ConnectionProvider {

    private static Logger          logger     = Logger.getLogger(DBManager.class);
    private static boolean         SHOW_STATS = false;
    private static BasicDataSource ds         = null;

    /**
     * Static helper method.
     * 
     * @return
     */
    public static Connection getDBConnection() {
        DBManager dbManager = new DBManager();
        return dbManager.getConnection();
    }

    /**
     * Gets a Connection from the pool.
     * 
     * @return
     */
    public Connection getConnection() {
        if (ds == null)
            ds = setupDataSource();
        Connection con = null;
        try {
            con = ds.getConnection();
            if (SHOW_STATS)
                showStats();
        } catch (SQLException e) {
            e.printStackTrace();
            return getConnection();
        }

        try {
            if (con == null || con.isClosed()) {
                Thread.sleep(1000);
                return getConnection();
            }
        } catch (Exception e) {
            return getConnection();
        }

        return con;
    }

    private static void showStats() {
        BasicDataSource test = (BasicDataSource) ds;
        System.out.println("Num Active: " + test.getNumActive());
        System.out.println("Num Idle: " + test.getNumIdle());
    }

    public static BasicDataSource getDataSource() {
        return (BasicDataSource) ds;
    }

    public static void log(String sql) {
        if (false)
            System.out.println(new Date() + ": " + sql);
    }

    /**
     * Safely closes the Connection, Statement, and ResultSet.
     * 
     * @param con
     * @param stmt
     * @param rs
     */
    public static void closeConnection(Connection con, Statement stmt, ResultSet rs) {
        try {
            if (con != null)
                con.close();
        } catch (Exception e) {
            System.out.println("Cannot close DB connection!");
        }
        try {
            if (stmt != null)
                stmt.close();
        } catch (Exception e) {
            System.out.println("Cannot close DB Statement!");
        }
        try {
            if (rs != null)
                rs.close();
        } catch (Exception e) {
            System.out.println("Cannot close DB ResultSet!");
        }
    }

    public static BasicDataSource setupDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("brkbrk1_brkbrk1");
        ds.setPassword("bre4ker1");
        ds.setUrl("jdbc:mysql://www.sportsbreaker.com:3306/brkbrk1_prod");
        ds.setInitialSize(5);
        ds.setMinIdle(5);
        ds.setMaxActive(10);
        ds.setMaxIdle(5);
        ds.setMaxWait(5000);
        ds.setValidationQuery("SELECT test FROM test_connection");
        ds.setTestOnBorrow(true);
        return ds;
    }

    public static void printDataSourceStats(DataSource ds) throws SQLException {
        BasicDataSource bds = (BasicDataSource) ds;
        System.out.println((new StringBuilder("NumActive: ")).append(bds.getNumActive()).toString());
        System.out.println((new StringBuilder("NumIdle: ")).append(bds.getNumIdle()).toString());
    }

    public static void shutdownDataSource(DataSource ds) throws SQLException {
        BasicDataSource bds = (BasicDataSource) ds;
        bds.close();
    }

    public void close() throws HibernateException {
        try {
            if (ds != null) {
                ds.close();
                ds = null;
            } else {
                logger.warn("Cannot close DBCP pool (not initialized)");
            }
        } catch (Exception e) {
            throw new HibernateException("Could not close DBCP pool", e);
        }

    }

    public void closeConnection(Connection con) throws SQLException {
        try {
            if (con != null)
                con.close();
        } catch (Exception e) {
            logger.warn("Could not close connection");
        }

    }

    public void configure(Properties props) throws HibernateException {
        // TODO Auto-generated method stub
    }

    public boolean supportsAggressiveRelease() {
        return false;
    }

}