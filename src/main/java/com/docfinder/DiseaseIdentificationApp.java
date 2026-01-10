package com.docfinder;

import com.docfinder.dao.UserDAO;
import com.docfinder.dao.DoctorDAO;
import com.docfinder.model.User;
import com.docfinder.model.Disease;
import com.docfinder.model.Doctor;
import com.docfinder.service.SymptomChecker;
import com.docfinder.exception.InvalidSymptomException;
import com.docfinder.exception.DiseaseNotFoundException;
import com.docfinder.dao.DiseaseDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DiseaseIdentificationApp {

    // Global variables so all methods can use them
    private static Scanner scanner = new Scanner(System.in);
    private static UserDAO userDAO = new UserDAO();
    private static SymptomChecker symptomChecker = new SymptomChecker();
    private static DoctorDAO doctorDAO = new DoctorDAO();
    private static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("       DOCFINDER - HEALTH SYSTEM       ");
        System.out.println("==========================================");

        boolean running = true;
        while (running) {
            // Main Menu
            System.out.println("\n1. Login");
            System.out.println("2. Register New User");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleLogin();
                    break;
                case "2":
                    handleRegistration();
                    break;
                case "3":
                    running = false;
                    System.out.println("Thank you for using DocFinder. Stay healthy!");
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    // --- USER ACTIONS ---

    private static void handleLogin() {
        System.out.print("\nEnter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        // Use the DAO logic we built in Phase 1
        User user = userDAO.getUserByUsername(username);

        // Simple password check (In a real app, use hashing!)
        if (user != null && user.getPasswordHash().equals(password)) {
            currentUser = user;
            System.out.println("✅ Login Successful! Welcome, " + user.getName());
            showUserDashboard(); // Go to the inner menu
        } else {
            System.out.println("❌ Invalid username or password.");
        }
    }

    private static void handleRegistration() {
        System.out.println("\n--- New User Registration ---");
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter Contact Number: ");
        String contact = scanner.nextLine();

        System.out.print("Choose a Username: ");
        String username = scanner.nextLine();

        System.out.print("Choose a Password: ");
        String password = scanner.nextLine();

        User newUser = new User(name, age, gender, contact, username, password);

        if (userDAO.registerUser(newUser)) {
            System.out.println("✅ Registration Successful! Please login.");
        } else {
            System.out.println("❌ Registration Failed. Username might be taken.");
        }
    }

    // --- THE DASHBOARD (Where the Logic happens) ---

    private static void showUserDashboard() {
        boolean loggedIn = true;
        DiseaseDAO diseaseDAO = new DiseaseDAO();

        while (loggedIn) {
            System.out.println("\n--- User Dashboard ---");
            System.out.println("1. Identify Disease (Check Symptoms)");
            System.out.println("2. View Available Symptoms");
            System.out.println("3. View Registered Doctors"); // <--- NEW OPTION
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    runSymptomChecker();
                    break;
                case "2":
                    showAllSymptoms(diseaseDAO);
                    break;
                case "3":
                    // <--- NEW CALL
                    showAllDoctors();
                    break;
                case "4":
                    loggedIn = false;
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void runSymptomChecker() {
        System.out.println("\nAvailable Symptoms in DB: Fever, Cough, Headache, Rash, Insomnia, etc.");
        System.out.println("Enter your symptoms separated by commas (e.g. Fever, Cough):");

        String input = scanner.nextLine();

        // 1. Convert string "Fever, Cough" into a Clean List
        List<String> symptomList = new ArrayList<>();
        String[] parts = input.split(",");
        for (String p : parts) {
            symptomList.add(p.trim()); // Remove extra spaces
        }

        try {
            System.out.println("🧠 Analyzing symptoms...");

            // 2. CALL THE LOGIC ENGINE (Phase 4)
            Disease result = symptomChecker.analyzeSymptoms(symptomList);

            // 3. DISPLAY RESULTS
            System.out.println("\n==========================================");
            System.out.println("🚨 DIAGNOSIS: " + result.getName().toUpperCase());
            System.out.println("==========================================");
            System.out.println("Category:    " + result.getCategory());
            System.out.println("Description: " + result.getDescription());

            System.out.println("\n🚑 FIRST AID STEPS:");
            if (result.getFirstAidSteps().isEmpty()) {
                System.out.println(" - No specific first aid steps available.");
            } else {
                for (String step : result.getFirstAidSteps()) {
                    System.out.println(" - " + step);
                }
            }

            // 4. RECOMMEND DOCTORS (Phase 5 Logic)
            System.out.println("\n👨‍⚕️ RECOMMENDED SPECIALISTS:");
            List<Doctor> doctors = doctorDAO.getDoctorsByDisease(result.getDiseaseID());

            if (doctors.isEmpty()) {
                System.out.println(" - No doctors found for this specific condition.");
            } else {
                for (Doctor d : doctors) {
                    // Using the display method we created in Doctor.java
                    System.out.println(" * " + d.getName() + " (" + d.getSpecialization() + ")");
                    System.out.println("   Address: " + d.getClinicAddress());
                    System.out.println("   Hours: " + d.getClinicHours());
                    System.out.println("   -------------------------------");
                }
            }
            System.out.println("==========================================\n");

        } catch (InvalidSymptomException e) {
            System.out.println("⚠️ Input Error: " + e.getMessage());
        } catch (DiseaseNotFoundException e) {
            System.out.println("⚠️ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void showAllSymptoms(DiseaseDAO dao) {
        System.out.println("\n📋 --- AVAILABLE SYMPTOMS ---");
        List<String> symptoms = dao.getAllSymptoms();

        if (symptoms.isEmpty()) {
            System.out.println("No symptoms found in the database.");
        } else {
            // Print them nicely in rows (Optional: simple list is fine too)
            int count = 0;
            for (String s : symptoms) {
                // Print with a bullet point
                System.out.print(" • " + String.format("%-20s", s));
                count++;

                // Start a new line after every 3 items to keep it readable
                if (count % 3 == 0) {
                    System.out.println();
                }
            }
            System.out.println("\n-----------------------------");
        }
    }

    private static void showAllDoctors() {
        System.out.println("\n👨‍⚕️ --- REGISTERED DOCTORS ---");
        List<Doctor> doctors = doctorDAO.getAllDoctors();

        if (doctors.isEmpty()) {
            System.out.println("No doctors found in the database.");
        } else {
            // Print a nice table-like format
            System.out.printf("%-25s | %-25s | %-15s\n", "Doctor Name", "Specialization", "Contact");
            System.out.println("----------------------------------------------------------------------");

            for (Doctor d : doctors) {
                System.out.printf("%-25s | %-25s | %-15s\n",
                        d.getName(),
                        d.getSpecialization(),
                        d.getContactNumber());
            }
            System.out.println("----------------------------------------------------------------------");
        }
    }

}