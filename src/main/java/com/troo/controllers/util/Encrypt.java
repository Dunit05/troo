// By: Suchir
// Sprint: 2

package com.troo.controllers.util;

import java.security.MessageDigest;

// Class to hash a password
public class Encrypt {
    // Method to SHA-256 hash a password, takes password as a string and returns the
    // hashed string
    public static String hash(String password) {
        // Local variables
        String hash = "";
        byte[] bytes;
        StringBuilder sb;

        // Try to hash the password
        try {
            // Create a MessageDigest instance
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            bytes = md.digest();
            sb = new StringBuilder();

            // For each byte in the bytes array, change it to a hexidecimal string and add
            // it to the StringBuilder
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            // Set the hash string to the hex string
            hash = sb.toString();

            // Return the hash string
            return hash;
        } catch (Exception e) {
            System.out.println("Error hashing password");
        }
        // Return an empty string if there is an error
        return "";
    }
}
