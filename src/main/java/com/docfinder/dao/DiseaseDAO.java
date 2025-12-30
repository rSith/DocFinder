package com.docfinder.dao;

import com.docfinder.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiseaseDAO {

    public List<Disease> getAllDiseases() {
        List<Disease> diseaseList = new ArrayList<>();
        String sql = "SELECT * FROM Disease";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String type = rs.getString("disease_type");
                Disease disease;

                // decide which child class to create
                if ("Rare".equalsIgnoreCase(type)) {
                    disease = new RareDisease();
                } else {
                    disease = new CommonDisease();
                }

                disease.setDiseaseID(rs.getInt("disease_id"));
                disease.setName(rs.getString("disease_name"));
                disease.setDescription(rs.getString("description"));
                disease.setCategory(rs.getString("category"));

                // Load extra details (Simplified helper methods below)
                loadSymptoms(disease);
                loadFirstAid(disease);

                diseaseList.add(disease);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diseaseList;
    }

    private void loadSymptoms(Disease disease) {
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

    private void loadFirstAid(Disease disease) {
        String sql = "SELECT step_description FROM FirstAidStep WHERE disease_id = " + disease.getDiseaseID();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                disease.addFirstAidStep(rs.getString("step_description"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}