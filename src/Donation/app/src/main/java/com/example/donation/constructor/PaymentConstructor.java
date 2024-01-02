package com.example.donation.constructor;

public class PaymentConstructor {
    String sender_name;
    String sender_email;
    String amount;
    String reciver_name;
    String reciver_email;
    String date;

    public PaymentConstructor() {
    }

    public PaymentConstructor(String sender_name, String sender_email, String amount, String reciver_name, String reciver_email, String date) {
        this.sender_name = sender_name;
        this.sender_email = sender_email;
        this.amount = amount;
        this.reciver_name = reciver_name;
        this.reciver_email = reciver_email;
        this.date = date;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_email() {
        return sender_email;
    }

    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReciver_name() {
        return reciver_name;
    }

    public void setReciver_name(String reciver_name) {
        this.reciver_name = reciver_name;
    }

    public String getReciver_email() {
        return reciver_email;
    }

    public void setReciver_email(String reciver_email) {
        this.reciver_email = reciver_email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
