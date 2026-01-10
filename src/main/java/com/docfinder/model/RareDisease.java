package com.docfinder.model;

import java.util.List;

public class RareDisease extends Disease {

    @Override
    public double calculateConfidence(List<String> userSymptoms) {
        int matchCount = 0;

        // Count matches
        for (int i = 0; i < userSymptoms.size(); i++) {
            String s = userSymptoms.get(i);
            if (getSymptoms().contains(s)) {
                matchCount++;
            }
        }

        // ⚠️ SAFETY RULE: Rare diseases (like Dengue) need at least 3 matches.
        // If less than 3, we assume it's unlikely to be this serious disease.
        if (matchCount < 3) return 0.0;

        return (double) matchCount / getSymptoms().size();
    }
}