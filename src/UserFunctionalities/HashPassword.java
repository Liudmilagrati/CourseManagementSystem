/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserFunctionalities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * HashPassword class provides methods for hashing passwords and generating salt.
 * It utilizes SHA-256 hashing algorithm for password hashing.
 * 
 * @author Liudmila Grati
 */
public class HashPassword {
    
    /**
     * Hashes the given plain password using SHA-256 algorithm with salt and iterations.
     * 
     * @param plainPassword The plain text password to be hashed.
     * @param salt          The salt to be used for password hashing.
     * @param iterations    The number of iterations for password hashing.
     * @return              The hashed password as a Base64 encoded string.
     */
    public static String hashPassword(String plainPassword, String salt, int iterations) {
        try {
            // Obtain instance of SHA-256 message digest
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Combine plain password with salt and convert to byte array
            byte[] hash = (plainPassword + salt).getBytes();
            // Perform specified number of iterations of hashing
            for (int i = 0; i < iterations; i++) {
                md.update(hash);
                hash = md.digest();
            }
            // Encode the resulting hash as a Base64 string and return
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            // Handle NoSuchAlgorithmException
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Generates a random salt for password hashing.
     * 
     * @return The generated salt as a Base64 encoded string.
     */
    public static String generateSalt() {
        // Create instance of SecureRandom for generating random bytes
        SecureRandom random = new SecureRandom();
        // Generate 16 random bytes for salt
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        // Encode the random bytes as a Base64 string and return
        return Base64.getEncoder().encodeToString(saltBytes);
    }
}
