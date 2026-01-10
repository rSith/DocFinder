package com.docfinder;

import com.docfinder.dao.DiseaseDAO;
import com.docfinder.dao.DoctorDAO;
import com.docfinder.model.Disease;
import com.docfinder.model.Doctor;
import java.util.Arrays;
import java.util.List;

public class TestAllLogic {

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("🧪  TESTING DOCFINDER LOGIC & DATABASE  🧪");
        System.out.println("==========================================\n");

        // --- STEP 1: TEST DISEASE LOADING ---
        System.out.println("--- [Step 1] Loading Diseases from DB ---");
        DiseaseDAO diseaseDAO = new DiseaseDAO();
        List<Disease> allDiseases = diseaseDAO.getAllDiseases();

        if (allDiseases.isEmpty()) {
            System.out.println("❌ Error: No diseases found. Check database connection.");
            return;
        }

        System.out.println("✅ Loaded " + allDiseases.size() + " diseases.");

        // Print the first disease to check details
        Disease firstDisease = allDiseases.get(0);
        System.out.println("   Example: " + firstDisease.getName());
        System.out.println("   Type:    " + firstDisease.getClass().getSimpleName()); // Checks Common vs Rare
        System.out.println("   Symptoms: " + firstDisease.getSymptoms());
        System.out.println("   First Aid: " + firstDisease.getFirstAidSteps());
        System.out.println();


        // --- STEP 2: TEST CALCULATION LOGIC ---
        System.out.println("--- [Step 2] Testing Diagnostic Logic ---");

        // Scenario: User has Fever and Cough
        List<String> userSymptoms = Arrays.asList("Fever", "Cough");
        System.out.println("User Input: " + userSymptoms);

        for (Disease d : allDiseases) {
            double score = d.calculateConfidence(userSymptoms);
            if (score > 0) {
                // Formatting %.2f makes it print like "0.67" instead of "0.666666"
                System.out.printf("   Match: %-15s Score: %.2f%%\n", d.getName(), (score * 100));
            }
        }
        System.out.println();


        // --- STEP 3: TEST DOCTOR FETCHING (NEW!) ---
        System.out.println("--- [Step 3] Testing Doctor Retrieval ---");
        DoctorDAO doctorDAO = new DoctorDAO();

        // Let's look for a doctor who treats "Depression"
        // (We need to find the ID for Depression first from our list)
        int targetDiseaseID = -1;
        String targetName = "Depression";

        for (Disease d : allDiseases) {
            if (d.getName().equalsIgnoreCase(targetName)) {
                targetDiseaseID = d.getDiseaseID();
                break;
            }
        }

        if (targetDiseaseID != -1) {
            System.out.println("Searching for doctors who treat: " + targetName + " (ID: " + targetDiseaseID + ")");
            List<Doctor> doctors = doctorDAO.getDoctorsByDisease(targetDiseaseID);

            if (doctors.isEmpty()) {
                System.out.println("❌ No doctors found.");
            } else {
                for (Doctor doc : doctors) {
                    System.out.println("   -----------------------------");
                    // This verifies your Specialization JOIN worked!
                    doc.displayInfo();
                }
                System.out.println("   -----------------------------");
                System.out.println("✅ Doctor test passed!");
            }
        } else {
            System.out.println("⚠️ Could not find disease '" + targetName + "' in the list to test doctors.");
        }
    }
}