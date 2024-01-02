package com.example.abnormal_person_app;

public class AuthConstructor {
    public String inputName;
    public String inputEmail;
    public String inputPassword;
    public String isActive;
    public String uid;

    public AuthConstructor() {
    }

    public AuthConstructor(String inputName, String inputEmail, String inputPassword, String isActive, String uid) {
        // Initialize the variables inside the constructor
        this.inputName = inputName;
        this.inputEmail = inputEmail;
        this.inputPassword = inputPassword;
        this.isActive = isActive;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIs_active() {
        return isActive;
    }

    public void setIs_active(String is_active) {
        this.isActive = is_active;
    }

    public String getName() {
        return inputName;
    }

    public void setName(String name) {
        this.inputName = name;
    }

    public String getEmail() {
        return inputEmail;
    }

    public void setEmail(String email) {
        this.inputEmail = email;
    }

    public String getPassword() {
        return inputPassword;
    }

    public void setPassword(String password) {
        this.inputPassword = password;
    }
}

