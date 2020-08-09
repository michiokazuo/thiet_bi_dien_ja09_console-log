package com.bksoftwarevn.itstudent.model;

import com.bksoftwarevn.itstudent.common.AppConfig;

import java.sql.*;

public class MyConnection {

    public static Connection connection = null;

    // Test Driver
    // Connection Success
    // Close connection

    public void driverTest() throws ClassNotFoundException {
        try {
            Class.forName(AppConfig.DRIVER);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("JDBC Driver Not Found" + e.getMessage());
        }
    }

    public Connection connectDB() throws SQLException, ClassNotFoundException {
        if (connection != null) return connection;

        driverTest();
        try {
            connection = DriverManager.getConnection(AppConfig.URL_DATABASE, AppConfig.USERNAME, AppConfig.PASSWORD);
            if (connection != null) {
                System.out.println("Connect DB Successed!");
            }
        } catch (Exception e) {
            throw new SQLException("Connect DB Failed!" + e.getMessage());
        }

        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("Connection is Closed!");
        }
    }

    // thực hiện câu lệnh select
    public PreparedStatement prepare(String sql) {
        try {
            System.out.println(">> " + sql);
            return connection.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // thao tác thêm, sửa, xóa
    public PreparedStatement prepareUpdate(String sql) {
        try {
            System.out.println(">> " + sql);
            return connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
