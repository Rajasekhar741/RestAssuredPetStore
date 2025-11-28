package com.petstore.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Utility class for generating test data
 */
public class TestDataBuilder {
    private static final Random random = new Random();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Generate a random pet ID
     */
    public static Long generatePetId() {
        return Math.abs(random.nextLong()) % 1000000 + 1;
    }

    /**
     * Generate a random username
     */
    public static String generateUsername() {
        return "user_" + System.currentTimeMillis();
    }

    /**
     * Generate a random email
     */
    public static String generateEmail() {
        return "test_" + System.currentTimeMillis() + "@petstore.com";
    }

    /**
     * Generate a random pet name
     */
    public static String generatePetName() {
        String[] names = {"Bella", "Max", "Charlie", "Luna", "Rocky", "Daisy", "Buddy", "Lucy"};
        return names[random.nextInt(names.length)];
    }

    /**
     * Get current timestamp
     */
    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(formatter);
    }

    /**
     * Generate a random password
     */
    public static String generatePassword() {
        return "Pass@" + System.currentTimeMillis();
    }

    /**
     * Generate random order ID
     */
    public static Long generateOrderId() {
        return Math.abs(random.nextLong()) % 1000000 + 1;
    }

    /**
     * Generate random quantity
     */
    public static Integer generateQuantity() {
        return random.nextInt(100) + 1;
    }
}
