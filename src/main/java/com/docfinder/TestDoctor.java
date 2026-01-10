package com.docfinder;

import com.docfinder.dao.DoctorDAO;
import com.docfinder.model.Doctor;
import java.util.List;

public class TestDoctor {
    public static void main(String[] args) {
        System.out.println("=== 🏥 Testing Doctor Database Connection ===\n");

        DoctorDAO dao = new DoctorDAO();

        // Test Case: Find doctors for Disease ID 1 (Common Cold)
        // Expected: Dr. GP Perera (General Practitioner)
        int testDiseaseId = 1;

        System.out.println("Fetching doctors for Disease ID: " + testDiseaseId);
        List<Doctor> doctors = dao.getDoctorsByDisease(testDiseaseId);

        if (doctors.isEmpty()) {
            System.out.println("❌ No doctors found. Check your database links!");
        } else {
            for (Doctor d : doctors) {
                System.out.println("--------------------------------");
                d.displayInfo(); // This prints the Name and Specialization
            }
            System.out.println("--------------------------------");
            System.out.println("✅ Success!");
        }
    }
}