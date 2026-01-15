package com.docfinder.util;

public class InputValidator {

    // Check if the phone number is exactly 10 digits
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    // Check if age is realistic (0 to 120)
    public static boolean isValidAge(int age) {
        return age > 0 && age <= 120;
    }

    // Check if password is strong enough (at least 4 chars)
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 4;
    }
}