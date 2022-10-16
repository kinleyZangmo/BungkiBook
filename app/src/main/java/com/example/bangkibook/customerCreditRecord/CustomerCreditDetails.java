package com.example.bangkibook.customerCreditRecord;

public class CustomerCreditDetails {
    String date,remark,addOrClear;
    int amount;

    public CustomerCreditDetails(String date,int amount, String remark, String addOrClear) {
        this.date = date;
        this.remark = remark;
        this.addOrClear = addOrClear;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddOrClear() {
        return addOrClear;
    }

    public void setAddOrClear(String addOrClear) {
        this.addOrClear = addOrClear;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
