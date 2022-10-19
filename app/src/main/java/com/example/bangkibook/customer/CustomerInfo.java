package com.example.bangkibook.customer;


import java.io.Serializable;

public class CustomerInfo implements Serializable {
    String Name,Email,StdId, PhoneNumber;
    float Credit;

    public CustomerInfo() {
    }
    public CustomerInfo(String name, String email, String phoneNumber, String stdId, float credit) {
        this.Name = name;
        this.Email = email;
        this.PhoneNumber = phoneNumber;
        this.StdId = stdId;
        this.Credit = credit;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.PhoneNumber = phoneNumber;
    }

    public String getStdId() {
        return StdId;
    }

    public void setStdId(String stdId) {
        this.StdId = stdId;
    }

    public float getCredit() {
        return Credit;
    }

    public void setCredit(float credit) {
        this.Credit = credit;
    }
}