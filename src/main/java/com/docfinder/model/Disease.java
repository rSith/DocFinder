package com.docfinder.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Disease {
    private int diseaseID;
    private String name;
    private String description;
    private String category;
    private List<String> symptoms = new ArrayList<>();
    private List<String> firstAidSteps = new ArrayList<>();

    public Disease() {}

    // Every disease must decide how it matches user symptoms
    public abstract double calculateConfidence(List<String> userSymptoms);

    // Getters and Setters
    public int getDiseaseID() {
        return diseaseID;
    }
    public void setDiseaseID(int diseaseID) {
        this.diseaseID = diseaseID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }
    public void addSymptom(String symptom) {
        this.symptoms.add(symptom);
    }

    public List<String> getFirstAidSteps() {
        return firstAidSteps;
    }
    public void addFirstAidStep(String step) {
        this.firstAidSteps.add(step);
    }
}