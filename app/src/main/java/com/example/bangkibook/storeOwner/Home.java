package com.example.bangkibook.storeOwner;

import android.app.Application;
import android.content.Intent;

import com.example.bangkibook.customer.CustomerLists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//This class will check active session of user.
public class Home extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

         //if user has not logged out they will be redirected to Customer List page even after closing the app.
        //This will avoid the user from having to login everytime they open the app.
        
        if(firebaseUser!=null){
            startActivity(new Intent(this, CustomerLists.class));
        }
    }
}
