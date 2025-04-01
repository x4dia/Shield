package me.xadia.shield.utils;

import java.util.concurrent.ThreadLocalRandom;

public class SaltUtil {

    private static final String SALT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    public static String getRandomSaltedString(int size) {
        StringBuilder saltedString = new StringBuilder();

        while (saltedString.length() < size) {
            int index = ThreadLocalRandom.current().nextInt(SALT_CHARS.length());
            saltedString.append(SALT_CHARS.charAt(index));
        }

        return saltedString.toString();
    }
}