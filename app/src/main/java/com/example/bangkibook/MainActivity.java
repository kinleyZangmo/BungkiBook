package com.example.bangkibook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginClick(View view) {
        // Intent intentLogin = new Intent(this, Login.class)
        // will be added with dechens code
    }

    // To
    public void signupClick(View view) {

        Intent intentSignup = new Intent(this, signup.class);
        startActivity(intentSignup);
        // intent takes to signup activity
    }
}