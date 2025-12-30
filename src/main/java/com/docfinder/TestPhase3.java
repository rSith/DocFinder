package com.docfinder;

import com.docfinder.dao.DiseaseDAO;
import com.docfinder.model.Disease;
import java.util.List;

public class TestPhase3 {
    public static void main(String[] args) {
        System.out.println("=== 🧪 Testing Phase 3: Disease Logic & Database ===\n");

        DiseaseDAO dao = new DiseaseDAO();

        System.out.println("1. Fetching diseases from database...");
        List<Disease> diseases = dao.getAllDiseases();

        if (diseases.isEmpty()) {
            System.out.println("❌ No diseases found. Did you run the SQL script in MySQL Workbench?");
        } else {
            System.out.println("✅ Success! Found " + diseases.size() + " diseases.\n");

            // Loop through each disease to check if data loaded correctly
            for (Disease d : diseases) {
                System.out.println("------------------------------------------------");
                System.out.println("Name:      " + d.getName());
                // This line proves Polymorphism work (CommonDisease vs RareDisease)
                System.out.println("Type:      " + d.getClass().getSimpleName());
                System.out.println("Category:  " + d.getCategory());
                System.out.println("Symptoms:  " + d.getSymptoms());
                System.out.println("First Aid: " + d.getFirstAidSteps());
                System.out.println("------------------------------------------------");
            }
        }
    }
}