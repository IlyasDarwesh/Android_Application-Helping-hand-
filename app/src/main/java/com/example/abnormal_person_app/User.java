package com.example.abnormal_person_app;

public class User {
    public String name;
    public int age; // Change age to int
    public String contact;
    public String imageUrl; // Add an imageUrl field

    public User() {
        // Default constructor required for Firebase
    }

    public User(String name, int age, String contact) { // Change age to int
        this.name = name;
        this.age = age; // Change age to int
        this.contact = contact;
    }

    // Add a constructor to include imageUrl
    public User(String name, int age, String contact, String imageUrl) { // Change age to int
        this.name = name;
        this.age = age; // Change age to int
        this.contact = contact;
        this.imageUrl = imageUrl;
    }
}
