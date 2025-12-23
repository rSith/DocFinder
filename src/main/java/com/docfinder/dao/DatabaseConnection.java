package com.docfinder.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        // 1. "db" tells Java to look for "db.properties" in resources automatically
        ResourceBundle reader = ResourceBundle.getBundle("db");

        // 2. Get the connection directly using the values
        return DriverManager.getConnection(
                reader.getString("db.url"),
                reader.getString("db.user"),
                reader.getString("db.password")
        );
    }
}