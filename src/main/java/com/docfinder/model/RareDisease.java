package com.docfinder.model;

import java.util.List;

public class RareDisease extends Disease {

    @Override
    public double calculateConfidence(List<String> userSymptoms) {

        int matchCount = 0;

        for (int i = 0; i < userSymptoms.size(); i++) {

            String s = userSymptoms.get(i);

            if (getSymptoms().contains(s)) {
                matchCount++;
            }
        }

        if (matchCount < 3) return 0.0;

        return (double) matchCount / getSymptoms().size();
    }
}