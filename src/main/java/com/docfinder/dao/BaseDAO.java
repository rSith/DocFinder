package com.docfinder.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDAO {

    // Protected so only child classes (UserDAO, DoctorDAO, etc.) can use it
    protected Connection getConnection() throws SQLException {
        try {
            // Reuses your existing connection logic
            return DatabaseConnection.getConnection();
        } catch (Exception e) {
            throw new SQLException("Error connecting to database via BaseDAO", e);
        }
    }
}