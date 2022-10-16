package com.example.bangkibook.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bangkibook.R;

public class CustomerProfile extends AppCompatActivity {
    TextView name,sID,email,phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        name=findViewById(R.id.c_name);
        sID=findViewById(R.id.c_sid);
        email=findViewById(R.id.c_email);
        phoneNo=findViewById(R.id.c_phoneNO);

        Intent i = getIntent();
        name.setText(i.getStringExtra("cName"));
        sID.setText(i.getStringExtra("cSid"));
        email.setText(i.getStringExtra("cEmail"));
        phoneNo.setText(i.getStringExtra("cPhoneNo"));



    }
}