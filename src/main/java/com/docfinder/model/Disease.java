package com.docfinder.model;

import java.util.ArrayList;
import java.util.List;

// Abstract: You cannot say "new Disease()"
public abstract class Disease {
    private int diseaseID;
    private String name;
    private String description;
    private String category;

    // We store symptoms and first aid steps as simple text lists
    private List<String> symptoms = new ArrayList<>();
    private List<String> firstAidSteps = new ArrayList<>();

    public Disease() {}

    // Abstract Method: Every child must define its own scoring math
    public abstract double calculateConfidence(List<String> userSymptoms);

    // Getters and Setters
    public int getDiseaseID() { return diseaseID; }
    public void setDiseaseID(int diseaseID) { this.diseaseID = diseaseID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    // List Helpers
    public List<String> getSymptoms() { return symptoms; }
    public void addSymptom(String symptom) { this.symptoms.add(symptom); }

    public List<String> getFirstAidSteps() { return firstAidSteps; }
    public void addFirstAidStep(String step) { this.firstAidSteps.add(step); }

    // Helper to display info (Useful for debugging)
    public void displayInfo() {
        System.out.println("Disease: " + name + " (" + category + ")");
    }
}