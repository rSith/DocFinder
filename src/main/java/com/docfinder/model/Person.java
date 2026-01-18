package com.docfinder.model;

public abstract class Person {

    // Encapsulation: private attributes
    private String name;           // Hidden data
    private int age;               // Hidden data
    private String gender;         // Hidden data
    private String contactNumber;  // Hidden data

    // Constructor
    public Person(String name, int age, String gender, String contactNumber) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contactNumber = contactNumber;
    }

    // Default constructor
    public Person() {
    }

    // Public methods to access data safely using Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        if (age > 0 && age < 120) this.age = age; // Validation!
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    // Abstract method that children must implement
    public abstract void displayInfo();
}