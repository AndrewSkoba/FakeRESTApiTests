package com.bookstore.datagenerator;

import com.bookstore.models.Author;

import java.util.Random;
import java.util.UUID;


public class AuthorGenerator {
    private static final Random RANDOM = new Random();
    private static final String[] FIRST_NAMES = {
            "John", "Jane", "Michael", "Sarah", "David", "Emily", "Robert", "Lisa",
            "William", "Jennifer", "James", "Mary", "Christopher", "Patricia", "Daniel"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller",
            "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez"
    };

    public static String generateUniqueId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public static String generateFirstName() {
        return FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)] + "_" + generateUniqueId();
    }

    public static String generateLastName() {
        return LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)] + "_" + generateUniqueId();
    }

    public static Author createRandomAuthor() {
        return Author.builder()
                .firstName(generateFirstName())
                .lastName(generateLastName())
                .idBook(generateBookId())
                .build();
    }

    public static int generateBookId() {
        return RANDOM.nextInt(200) + 1;
    }

}

