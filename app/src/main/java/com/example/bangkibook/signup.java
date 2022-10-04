package com.example.bangkibook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class signup extends AppCompatActivity {

    private EditText storeName,email,phoneNo,password,confirmPassword;
    Button buttonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        storeName = findViewById(R.id.store_name);
        email=findViewById(R.id.email);
        phoneNo=findViewById(R.id.phone_no);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirm_password);
        buttonCreateAccount=findViewById(R.id.createA);

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Obtained The Entered Date

                String Store_Name = storeName.getText().toString();
                String Store_Email= email.getText().toString();
                String Store_PhoneNo= phoneNo.getText().toString();
                String Store_Password= password.getText().toString();
                String Store_ConfirmPassword= confirmPassword.getText().toString();

                //Check if values are entered and if they are valid
                if(TextUtils.isEmpty(Store_Name)){
                    storeName.setError("Store Name is Required");
                    storeName.requestFocus();
                }else if(TextUtils.isEmpty(Store_Email)) {
                    email.setError("Email is Required");
                    email.requestFocus();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(Store_Email).matches()) {
                    email.setError("Valid email is Required");
                    email.requestFocus();
                }else if(TextUtils.isEmpty(Store_PhoneNo)) {
                        phoneNo.setError("Phone No is Required");
                        phoneNo.requestFocus();
                } else if(Store_PhoneNo.length()!=8){
                    phoneNo.setError("Phone no should be 10 digits");
                    phoneNo.requestFocus();
                }else if(TextUtils.isEmpty(Store_Password)){
                    password.setError("Password Required");
                    password.requestFocus();
                }else if(TextUtils.isEmpty(Store_ConfirmPassword)){
                    confirmPassword.setError("Confirm password is Required");
                    confirmPassword.requestFocus();
                }else if(!Store_ConfirmPassword.equals(Store_Password)){
                     confirmPassword.setError("Password Does Not Match");
                    confirmPassword.requestFocus();
                }else{
                    Toast.makeText(signup.this, "Account Created", Toast.LENGTH_LONG).show();
                }


            }
        });




    }

}