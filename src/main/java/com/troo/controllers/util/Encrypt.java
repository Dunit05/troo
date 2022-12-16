// By: Tommy
// Sprint: 2

package com.troo.controllers.util;

import java.security.MessageDigest;

// Class to hash a password
public class Encrypt {
    // Method to SHA-256 hash a password, takes password as a string and returns the
    // hash string
    public static String hash(String password) {
        // Local variables
        String hash = "";
        byte[] bytes;

        // Try to hash the password
        try {
            // Create a MessageDigest instance
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            bytes = md.digest();
            StringBuilder sb = new StringBuilder();

            // Convert the bytes to a hex string
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            // Set the hash string to the hex string
            hash = sb.toString();

            // Return the hash string
            return hash;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
