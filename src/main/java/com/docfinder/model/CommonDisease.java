package com.docfinder.model;

import java.util.List;

public class CommonDisease extends Disease {

    @Override
    public double calculateConfidence(List<String> userSymptoms) {
        if (getSymptoms().isEmpty()) return 0.0;

        int matchCount = 0;
        // Simple logic: Count how many symptoms match
        for (int i = 0; i < userSymptoms.size(); i++) {
            String s = userSymptoms.get(i);
            if (getSymptoms().contains(s)) {
                matchCount++;
            }
        }

        // Return percentage (e.g., 2/3 = 0.66)
        return (double) matchCount / getSymptoms().size();
    }
}