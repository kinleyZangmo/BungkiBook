package com.example.bangkibook.storeOwner;

public class ReadWriteOwnerDetails {
    public String Store_name,Store_email,Store_phoneNo,password;

    public ReadWriteOwnerDetails(String T_name, String T_email, String T_phoneNo, String T_password){
        this.Store_name=T_name;
        this.Store_email=T_email;
        this.Store_phoneNo=T_phoneNo;
        this.password=T_password;
    }
    public String getName(){
        return Store_name;
    }
    public String getEmail(){
        return Store_email;
    }
    public String getPhoneNo(){
        return Store_phoneNo;
    }
    public void setName(String name){
        this.Store_name = name;
    }
    public void setEmail(String email){
        this.Store_email =email ;
    }
    public void setPhoneNo(String phoneNo){
        this.Store_phoneNo =phoneNo ;
    }
}
