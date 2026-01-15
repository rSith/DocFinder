package com.docfinder.dao;

import com.docfinder.model.User;
import java.sql.*;

public class UserDAO extends BaseDAO { // <--- INHERITANCE

    public boolean registerUser(User user) {
        String[] names = user.getName().split(" ", 2);
        String firstName = names[0];
        String lastName = names.length > 1 ? names[1] : ".";

        String sql = "INSERT INTO User (username, password_hash, first_name, last_name, age, gender, contact_number) " +
                "VALUES ('" + user.getUsername() + "', '" + user.getPasswordHash() + "', '" +
                firstName + "', '" + lastName + "', " + user.getAge() + ", '" +
                user.getGender() + "', '" + user.getContactNumber() + "')";

        // Use the inherited getConnection() method
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsInserted = stmt.executeUpdate(sql);
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = '" + username + "'";

        try (Connection conn = getConnection(); // <--- INHERITED METHOD
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