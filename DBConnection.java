package com.example.rastuarent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // MySQL database URL, username, password
    private static final String URL = "jdbc:mysql://localhost:3306/lunch?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";           // your MySQL user
    private static final String PASSWORD = "123456"; // your MySQL password

    // Load the driver once when the class is loaded
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // explicit driver load
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Private constructor to prevent instantiation
    private DBConnection() { }

    // Method to get connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}