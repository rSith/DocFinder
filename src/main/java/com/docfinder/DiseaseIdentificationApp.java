package com.docfinder;

import static com.docfinder.dao.DatabaseConnection.getConnection;

public class DiseaseIdentificationApp {
    public static void main(String[] args) {
        try {
            com.docfinder.dao.DatabaseConnection.getConnection();
            System.out.println("✅ Database Connection Test Passed!");
        } catch (java.sql.SQLException e) {
            System.out.println("❌ Database Connection Failed: " + e.getMessage());
        }
    }
}