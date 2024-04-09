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
 *
 * @author user
 */
public class HashPassword {
    
    public static String hashPassword(String plainPassword, String salt, int iterations) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = (plainPassword + salt).getBytes();
            for (int i = 0; i < iterations; i++) {
                md.update(hash);
                hash = md.digest();
            }
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
    
}
