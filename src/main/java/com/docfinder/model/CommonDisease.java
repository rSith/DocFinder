package com.docfinder.model;

import java.util.List;

public class CommonDisease extends Disease {

    @Override
    public double calculateConfidence(List<String> userSymptoms) {

        List<String> diseaseSymptoms = getSymptoms();

        if (diseaseSymptoms.isEmpty()) {
            return 0.0;
        }

        int matches = 0;

        for (int i = 0; i < userSymptoms.size(); i++) {

            String input = userSymptoms.get(i);

            if (diseaseSymptoms.contains(input)) {
                matches++;
            }
        }

        return (double) matches / diseaseSymptoms.size();
    }
}