package com.app;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class SQLConnection {
    private static Connection connection = null;

    static {
        Properties config = new Properties();
        try {

            //config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));

            config.load(new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\props\\config.properties"));
            String url = "jdbc:sqlserver://" + config.getProperty("serverName") + ";databaseName=" + config.getProperty("db.name");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, config.getProperty("db.login"), config.getProperty("db.password"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private SQLConnection(){}

    public static Connection getConnection(){

        return connection;
    }
}
