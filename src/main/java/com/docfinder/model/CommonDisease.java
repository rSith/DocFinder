package com.docfinder.model;

import java.util.List;

// INHERITANCE: Extends the abstract 'Disease' class
public class CommonDisease extends Disease {

    // POLYMORPHISM: This method overrides the parent's abstract method.
    // The diagnostic engine calls this specific version when the disease is "Common".
    @Override
    public double calculateConfidence(List<String> userSymptoms) {

        // Safety check: If the disease has no symptoms defined in DB, avoid division by zero
        if (getSymptoms().isEmpty()) return 0.0;

        int matchCount = 0;

        // Loop through every symptom the user selected
        for (int i = 0; i < userSymptoms.size(); i++) {
            String s = userSymptoms.get(i);

            // Check if this disease contains the user's symptom
            if (getSymptoms().contains(s)) {
                matchCount++; // Increment if there is a match
            }
        }

        // Return the simple percentage match (e.g., 2 matches / 3 total symptoms = 0.66 or 66%)
        return (double) matchCount / getSymptoms().size();
    }
}