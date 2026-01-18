package com.docfinder.model;

import java.util.List;

// INHERITANCE: Also extends 'Disease', so it can be treated as a Disease object
public class RareDisease extends Disease {

    // POLYMORPHISM: Overrides the SAME method signature but with DIFFERENT logic.
    @Override
    public double calculateConfidence(List<String> userSymptoms) {

        int matchCount = 0;

        // Standard counting logic (same as CommonDisease)
        for (int i = 0; i < userSymptoms.size(); i++) {
            String s = userSymptoms.get(i);
            if (getSymptoms().contains(s)) {
                matchCount++;
            }
        }

        // --- THE POLYMORPHIC DIFFERENCE ---
        // SAFETY RULE: Rare diseases (like Dengue) are serious.
        // We require at least 3 matching symptoms to even consider it.
        // If the user has fewer than 3 matches, we force the confidence to 0%.
        if (matchCount < 3) return 0.0;

        // If it passes the safety check, calculate the percentage as normal
        return (double) matchCount / getSymptoms().size();
    }
}