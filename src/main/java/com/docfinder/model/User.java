package com.docfinder.model;

// Child class inherits from Person
public class User extends Person {
    private int userID;
    private String username;
    private String passwordHash;

    public User(String name, int age, String gender, String contactNumber, String username, String passwordHash) {
        // Passes common data to Parent (Person)
        super(name, age, gender, contactNumber);
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public User(){}

    // Getters and Setters
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public void displayInfo() {
        System.out.println("User: " + getName() + " (" + username + ")");
    }
}