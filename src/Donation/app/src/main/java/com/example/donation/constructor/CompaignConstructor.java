package com.example.donation.constructor;

public class CompaignConstructor {

    String title;
    String description;
    String amount;
    String target;
    String start;
    String end;
    String status;

    public CompaignConstructor() {
    }

    public CompaignConstructor(String title, String description, String amount, String target, String start, String end, String status) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.target = target;
        this.start = start;
        this.end = end;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
