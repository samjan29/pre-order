package com.pre_order.order_service.global.security.encrypt;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AESKeyGenerator {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Create a KeyGenerator with the AES algorithm
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");

        // Initialize the KeyGenerator to create a 256 bit key
        SecureRandom secureRandom = new SecureRandom();
        keyGen.init(256, secureRandom);

        // Generate the key
        byte[] aesKey = keyGen.generateKey().getEncoded();
        String aesKeyBase64 = Base64.getEncoder().encodeToString(aesKey);
        System.out.println("AES Key: " + aesKeyBase64);

        // Generate a random IV
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        String ivBase64 = Base64.getEncoder().encodeToString(iv);
        System.out.println("IV: " + ivBase64);
    }
}