package com.docfinder.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {

    /**
     * Hashes a raw password (e.g., "pass123") into a secure string.
     * Your App calls this method 'hash' or 'hashPassword'.
     */
    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Verifies if a raw password matches the stored hash.
     * This is the method causing your error!
     */
    public static boolean verify(String rawPassword, String storedHash) {
        // 1. Hash the input
        String newHash = hash(rawPassword);
        // 2. Compare it with the database hash
        return newHash.equals(storedHash);
    }
}