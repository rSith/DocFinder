package com.docfinder.dao;

import com.docfinder.model.Doctor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    // Fetch doctors who treat a specific disease
    public List<Doctor> getDoctorsByDisease(int diseaseID) {
        List<Doctor> doctors = new ArrayList<>();

        String sql = "SELECT d.*, s.specialization_name " +
                "FROM Doctor d " +
                "JOIN specialization s ON d.specialization_id = s.specialization_id " +
                "JOIN DoctorDisease dd ON d.doctor_id = dd.doctor_id " +
                "WHERE dd.disease_id = " + diseaseID;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Doctor doc = new Doctor();
                doc.setDoctorID(rs.getInt("doctor_id"));

                // Inherited fields from Person class
                doc.setName(rs.getString("name"));
                doc.setContactNumber(rs.getString("contact_number"));

                // Doctor specific fields
                // Note: We get 'specialization_name' because of the JOIN above
                doc.setSpecialization(rs.getString("specialization_name"));
                doc.setClinicAddress(rs.getString("clinic_address"));
                doc.setClinicHours(rs.getString("clinic_hours"));

                doctors.add(doc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    // New Method: Fetch ALL doctors to show in the menu
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();

        // Simple JOIN to get the doctor name + specialization name
        String sql = "SELECT d.*, s.specialization_name " +
                "FROM Doctor d " +
                "JOIN specialization s ON d.specialization_id = s.specialization_id " +
                "ORDER BY d.name ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Doctor doc = new Doctor();
                doc.setDoctorID(rs.getInt("doctor_id"));
                doc.setName(rs.getString("name"));
                doc.setContactNumber(rs.getString("contact_number"));
                doc.setSpecialization(rs.getString("specialization_name"));
                doc.setClinicAddress(rs.getString("clinic_address"));
                doc.setClinicHours(rs.getString("clinic_hours"));

                doctors.add(doc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

}