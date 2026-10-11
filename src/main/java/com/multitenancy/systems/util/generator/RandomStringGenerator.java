package com.multitenancy.systems.util.generator;

import java.util.Random;

public class RandomStringGenerator {

    public RandomStringGenerator() {}

    public String generateString(Integer length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        return salt.toString();
    }

}
