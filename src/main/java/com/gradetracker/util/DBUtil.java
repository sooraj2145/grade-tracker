package com.gradetracker.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {
    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    static {
        try {
            Properties props = new Properties();

            InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            props.load(input);
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");

            Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException("Database config failed", e);
        }
    }

    public static Connection getConnection() {
        try{
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to database",e);
        }
    }
}
