package com.docfinder.dao;

import com.docfinder.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiseaseDAO {

    public List<Disease> getAllDiseases() {
        List<Disease> diseaseList = new ArrayList<>();

        // 1. Select all diseases
        String sql = "SELECT * FROM Disease";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String type = rs.getString("disease_type");
                Disease disease;

                // 2. Factory Logic: Decide which class to create
                if ("Rare".equalsIgnoreCase(type)) {
                    disease = new RareDisease();
                } else {
                    disease = new CommonDisease();
                }

                // 3. Populate basic details
                disease.setDiseaseID(rs.getInt("disease_id"));
                disease.setName(rs.getString("disease_name"));
                disease.setDescription(rs.getString("description"));
                disease.setCategory(rs.getString("category"));

                // 4. Load related data (Symptoms & First Aid)
                loadSymptoms(disease);
                loadFirstAid(disease);

                diseaseList.add(disease);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diseaseList;
    }

    // Helper: Fetch symptoms for a specific disease
    private void loadSymptoms(Disease disease) {
        // JOIN query to get symptom names linked to this disease ID
        String sql = "SELECT s.symptom_name FROM Symptom s " +
                "JOIN DiseaseSymptom ds ON s.symptom_id = ds.symptom_id " +
                "WHERE ds.disease_id = " + disease.getDiseaseID();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                disease.addSymptom(rs.getString("symptom_name"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Helper: Fetch first aid steps for a specific disease
    private void loadFirstAid(Disease disease) {
        // Query FirstAidStep table, ordered by step_order
        String sql = "SELECT step_description FROM FirstAidStep " +
                "WHERE disease_id = " + disease.getDiseaseID() +
                " ORDER BY step_order ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                disease.addFirstAidStep(rs.getString("step_description"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // New Method: Fetch all unique symptoms to show the user
    public List<String> getAllSymptoms() {
        List<String> symptoms = new ArrayList<>();
        String sql = "SELECT symptom_name FROM Symptom ORDER BY symptom_name ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                symptoms.add(rs.getString("symptom_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return symptoms;
    }
}