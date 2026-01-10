package com.docfinder.service;

import com.docfinder.dao.DiseaseDAO;
import com.docfinder.model.Disease;
import com.docfinder.exception.DiseaseNotFoundException;
import com.docfinder.exception.InvalidSymptomException;
import java.util.List;

public class SymptomChecker {

    private DiseaseDAO diseaseDAO;

    public SymptomChecker() {
        // Connect to the database "Librarian"
        this.diseaseDAO = new DiseaseDAO();
    }

    public Disease analyzeSymptoms(List<String> userSymptoms)
            throws InvalidSymptomException, DiseaseNotFoundException {

        // 1. Validation: specific requirement from guide
        if (userSymptoms == null || userSymptoms.isEmpty()) {
            throw new InvalidSymptomException("Please provide at least one symptom.");
        }

        // 2. Get all known diseases from the Database
        List<Disease> allDiseases = diseaseDAO.getAllDiseases();

        Disease bestMatch = null;
        double highestScore = 0.0;

        // 3. The Logic Loop: Check every disease one by one
        for (int i = 0; i < allDiseases.size(); i++) {

            Disease currentDisease = allDiseases.get(i);

            // Ask the disease: "How much do these symptoms match you?"
            // (Polymorphism: Common and Rare diseases calculate this differently!)
            double score = currentDisease.calculateConfidence(userSymptoms);

            // If this disease is a better match than the previous one, save it
            if (score > highestScore) {
                highestScore = score;
                bestMatch = currentDisease;
            }
        }

        // 4. Threshold Check (Must be at least 40% confident)
        // If the score is too low, we don't want to guess randomly.
        if (bestMatch == null || highestScore < 0.4) {
            throw new DiseaseNotFoundException("We could not identify a disease with high confidence. Please see a doctor immediately.");
        }

        return bestMatch;
    }
}