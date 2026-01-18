package com.docfinder.model;

import java.util.List;

// INHERITANCE: Extends the abstract 'Disease' class
public class CommonDisease extends Disease {

    // POLYMORPHISM: This method overrides the parent's abstract method.
    @Override
    public double calculateConfidence(List<String> userSymptoms) {

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
        return (double) matchCount / getSymptoms().size();
    }
}

