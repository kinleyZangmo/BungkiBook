package com.example.bangkibook.storeOwner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bangkibook.R;

public class OwnerProfile extends AppCompatActivity {
    TextView name,email,phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);

        name=findViewById(R.id.storeName);
        email=findViewById(R.id.storeEmail);
        phoneNo=findViewById(R.id.storePhoneNo);
    }

    //Logout Session
    public void logout(View view) {

        Toast.makeText(this, "Logged Out", Toast.LENGTH_LONG).show();
        Intent logout = new Intent(this,OwnerMainActivity.class);
        startActivity(logout);
    }
}