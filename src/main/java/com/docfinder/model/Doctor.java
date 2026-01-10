package com.docfinder.model;

public class Doctor extends Person {
    private int doctorID;
    private String specialization; // We store the NAME here (fetched via JOIN)
    private String clinicAddress;
    private String clinicHours;

    public Doctor() {}

    // Getters and Setters
    public int getDoctorID() { return doctorID; }
    public void setDoctorID(int doctorID) { this.doctorID = doctorID; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getClinicAddress() { return clinicAddress; }
    public void setClinicAddress(String clinicAddress) { this.clinicAddress = clinicAddress; }

    public String getClinicHours() { return clinicHours; }
    public void setClinicHours(String clinicHours) { this.clinicHours = clinicHours; }

    @Override
    public void displayInfo() {
        // Output format: Dr. Name (Specialization)
        System.out.println("   Name: " + getName() + " (" + specialization + ")");
        System.out.println("   Clinic: " + clinicAddress);
        System.out.println("   Hours:  " + clinicHours);
    }
}