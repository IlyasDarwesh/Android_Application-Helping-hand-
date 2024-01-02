package com.example.donation.constructor;

public class TrustConstructor {
    String name;
    String email;
    String type;
    String passwrod;

    public TrustConstructor() {
    }

    public TrustConstructor(String name, String email, String type, String passwrod) {
        this.name = name;
        this.email = email;
        this.type = type;
        this.passwrod = passwrod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPasswrod() {
        return passwrod;
    }

    public void setPasswrod(String passwrod) {
        this.passwrod = passwrod;
    }
}
