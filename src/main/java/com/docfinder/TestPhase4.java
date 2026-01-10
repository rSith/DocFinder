package com.docfinder;

import com.docfinder.service.SymptomChecker;
import com.docfinder.model.Disease;
import java.util.Arrays;
import java.util.List;

public class TestPhase4 {
    public static void main(String[] args) {
        System.out.println("=== 🧠 Testing Phase 4: The Logic Engine ===\n");

        SymptomChecker checker = new SymptomChecker();

        // SCENARIO 1: User has 'Fever' and 'Cough'
        // This should match 'Common Cold' based on our database data
        List<String> mySymptoms = Arrays.asList("Fever", "Cough", "Headache");

        try {
            System.out.println("User Input: " + mySymptoms);

            // Run the brain
            Disease result = checker.analyzeSymptoms(mySymptoms);

            System.out.println("✅ IDENTIFIED: " + result.getName());
            System.out.println("   Description: " + result.getDescription());

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}