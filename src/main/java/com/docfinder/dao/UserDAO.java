package com.docfinder.dao;

import com.docfinder.model.User;
import java.sql.*;

public class UserDAO {

    // 1. Register a new User (Direct String Concatenation)
    public boolean registerUser(User user) {

        // Logic to split the name
        String[] names = user.getName().split(" ", 2);
        String firstName = names[0];
        String lastName = names.length > 1 ? names[1] : ".";

        String sql = "INSERT INTO User (username, password_hash, first_name, last_name, age, gender, contact_number) " +
                "VALUES ('" + user.getUsername() + "', " +
                "'" + user.getPasswordHash() + "', " +
                "'" + firstName + "', " +
                "'" + lastName + "', " +
                user.getAge() + ", " +
                "'" + user.getGender() + "', " +
                "'" + user.getContactNumber() + "')";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsInserted = stmt.executeUpdate(sql);
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Login Check (Direct String Concatenation)
    public User getUserByUsername(String username) {

        String sql = "SELECT * FROM User WHERE username = '" + username + "'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String fullName = rs.getString("first_name") + " " + rs.getString("last_name");

                return new User(
                        fullName,
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("contact_number"),
                        rs.getString("username"),
                        rs.getString("password_hash")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}