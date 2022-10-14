package com.example.bangkibook.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.bangkibook.R;

public class CustomerAdd extends AppCompatActivity {

    private EditText name,sId,email,phoneNo;
    Button buttonCreateCustomer;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add);

        name=findViewById(R.id.name);
        sId=findViewById(R.id.stdid);
        email=findViewById(R.id.email);
        phoneNo=findViewById(R.id.phoneno);
        buttonCreateCustomer=findViewById(R.id.createC);
        progressBar=findViewById(R.id.progressbar);

        buttonCreateCustomer.setOnClickListener(view -> {
            //Obtain data from input fields
            String Customer_name =name.getText().toString();
            String Customer_sId=sId.getText().toString();
            String Customer_email=email.getText().toString();
            String Customer_phoneNo=phoneNo.getText().toString();

            //Validate entered data
            if(TextUtils.isEmpty(Customer_name)){
                name.setError("Name is required");
                name.requestFocus();
            }else if(TextUtils.isEmpty(Customer_sId)){
                sId.setError("Student Id is required");
                sId.requestFocus();
            }
            else if(Customer_sId.length()!=8){
                sId.setError("Student Id is invalid");
                sId.requestFocus();
            }
            else if(TextUtils.isEmpty(Customer_email)){
                email.setError("Email is required");
                email.requestFocus();
            }else if(!Patterns.EMAIL_ADDRESS.matcher(Customer_email).matches()){
                email.setError("Valid email is required");
                email.requestFocus();
            }else if(TextUtils.isEmpty(Customer_phoneNo)){
                phoneNo.setError("Phone No is required");
                phoneNo.requestFocus();
            }else if(Customer_phoneNo.length()!=8){
                phoneNo.setError("Phone no should be 8 digits");
                phoneNo.requestFocus();
            }else{
                progressBar.setVisibility(View.VISIBLE);
                createAccount(Customer_name,Customer_sId,Customer_email,Customer_phoneNo);
            }


        });
    }

    private void createAccount(String customer_name, String customer_sId, String customer_email, String customer_phoneNo) {
        //
    }
}