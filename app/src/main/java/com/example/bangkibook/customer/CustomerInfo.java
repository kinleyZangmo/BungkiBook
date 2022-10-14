package com.example.bangkibook.customer;

public class CustomerInfo {
    String name,email,stdId;
    int phoneNumber;
    float credit;

    public CustomerInfo() {
    }

    public CustomerInfo(String name, String email, int phoneNumber, String stdId, float credit) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.stdId = stdId;
        this.credit = credit;
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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStdId() {
        return stdId;
    }

    public void setStdId(String stdId) {
        this.stdId = stdId;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }
}
