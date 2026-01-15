package com.docfinder.dao;

import com.docfinder.model.Doctor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO extends BaseDAO { // <--- INHERITANCE

    public List<Doctor> getDoctorsByDisease(int diseaseID) {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT d.*, s.specialization_name " +
                "FROM Doctor d " +
                "JOIN specialization s ON d.specialization_id = s.specialization_id " +
                "JOIN DoctorDisease dd ON d.doctor_id = dd.doctor_id " +
                "WHERE dd.disease_id = " + diseaseID;

        try (Connection conn = getConnection(); // <--- INHERITED METHOD
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

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT d.*, s.specialization_name " +
                "FROM Doctor d " +
                "JOIN specialization s ON d.specialization_id = s.specialization_id " +
                "ORDER BY d.name ASC";

        try (Connection conn = getConnection(); // <--- INHERITED METHOD
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