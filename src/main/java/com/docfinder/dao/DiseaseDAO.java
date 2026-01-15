package com.docfinder.dao;

import com.docfinder.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiseaseDAO extends BaseDAO { // <--- INHERITANCE

    public List<Disease> getAllDiseases() {
        List<Disease> diseaseList = new ArrayList<>();
        String sql = "SELECT * FROM Disease";

        try (Connection conn = getConnection(); // <--- INHERITED METHOD
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String type = rs.getString("disease_type");
                Disease disease;

                // Factory Logic
                if ("Rare".equalsIgnoreCase(type)) {
                    disease = new RareDisease();
                } else {
                    disease = new CommonDisease();
                }

                disease.setDiseaseID(rs.getInt("disease_id"));
                disease.setName(rs.getString("disease_name"));
                disease.setDescription(rs.getString("description"));
                disease.setCategory(rs.getString("category"));

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

        try (Connection conn = getConnection(); // <--- INHERITED METHOD
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                disease.addSymptom(rs.getString("symptom_name"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void loadFirstAid(Disease disease) {
        String sql = "SELECT step_description FROM FirstAidStep " +
                "WHERE disease_id = " + disease.getDiseaseID() +
                " ORDER BY step_order ASC";

        try (Connection conn = getConnection(); // <--- INHERITED METHOD
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                disease.addFirstAidStep(rs.getString("step_description"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<String> getAllSymptoms() {
        List<String> symptoms = new ArrayList<>();
        String sql = "SELECT symptom_name FROM Symptom ORDER BY symptom_name ASC";

        try (Connection conn = getConnection(); // <--- INHERITED METHOD
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