package com.example.ceshi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    // 直接将数据库连接信息放在这里
    static String url = "jdbc:mysql://localhost:3306/personal_contacts";
    static String username = "root";
    static String password = "root";
    static Connection conn = null;

    static {
        init();
    }

    public static void init() {
        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取连接
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/personal_contacts", "root", "root");
            System.out.println(connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection!");
            e.printStackTrace();
        }
    }
}
