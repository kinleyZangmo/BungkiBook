package com.example.bangkibook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class signup extends AppCompatActivity {

    EditText storeName,email,phoneNo,password,confirmPassword;
    Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        storeName = findViewById(R.id.store_name);
        email=findViewById(R.id.email);
        phoneNo=findViewById(R.id.phone_no);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirm_password);

    }
    public void Register(View view) {

    }
}