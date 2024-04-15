package com.rewards.backend.utility;

import java.security.SecureRandom;

public class RandomNumberGenerator {

    private static final int MIN_4_DIGIT_VALUE = 1000;
    private static final int MAX_4_DIGIT_VALUE = 9999;
    private static final int MIN_6_DIGIT_VALUE = 100000;
    private static final int MAX_6_DIGIT_VALUE = 999999;

    private static final String ALPHA_NUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static SecureRandom secureRandom = new SecureRandom();

    private RandomNumberGenerator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    
    public static String generate4DigitCode() {
        int code = MIN_4_DIGIT_VALUE + secureRandom.nextInt(MAX_4_DIGIT_VALUE - MIN_4_DIGIT_VALUE + 1);
        return String.format("%04d", code);
    }

    public static String generate6DigitCode() {
        int code = MIN_6_DIGIT_VALUE + secureRandom.nextInt(MAX_6_DIGIT_VALUE - MIN_6_DIGIT_VALUE + 1);
        return String.format("%06d", code);
    }

    public static String generateAlphanumeric6DigitCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = secureRandom.nextInt(ALPHA_NUMERIC_CHARACTERS.length());
            code.append(ALPHA_NUMERIC_CHARACTERS.charAt(index));
        }
        return code.toString();
    }

    public static String generateAlphanumeric8DigitCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = secureRandom.nextInt(ALPHA_NUMERIC_CHARACTERS.length());
            code.append(ALPHA_NUMERIC_CHARACTERS.charAt(index));
        }
        return code.toString();
    }
    
    public static String generate12DigitCode() {
        return generateNumericCode(12);
    }

    public static String generate18DigitCode() {
        return generateNumericCode(18);
    }

    public static String generateCustomLengthCode(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(secureRandom.nextInt(10)); // Append a random digit (0-9)
        }
        return code.toString();
    }

    private static String generateNumericCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(secureRandom.nextInt(10)); // Append a random digit (0-9)
        }
        return code.toString();
    }
    
    
}
