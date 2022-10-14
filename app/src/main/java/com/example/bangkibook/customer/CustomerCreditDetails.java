package com.example.bangkibook.customer;

public class CustomerCreditDetails {
    String date,remarks,addOrClear;
    int creditId, amount;
    String stdId;

    public CustomerCreditDetails(String date, String remarks, String addOrClear, int creditId, int amount) {
        this.date = date;
        this.remarks = remarks;
        this.addOrClear = addOrClear;
        this.creditId = creditId;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAddOrClear() {
        return addOrClear;
    }

    public void setAddOrClear(String addOrClear) {
        this.addOrClear = addOrClear;
    }

    public int getCreditId() {
        return creditId;
    }

    public void setCreditId(int creditId) {
        this.creditId = creditId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStdId() {
        return stdId;
    }

    public void setStdId(String stdId) {
        this.stdId = stdId;
    }
}
