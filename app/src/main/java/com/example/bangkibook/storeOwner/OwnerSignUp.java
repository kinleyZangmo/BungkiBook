package com.example.bangkibook.storeOwner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bangkibook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OwnerSignUp extends AppCompatActivity {
    Dialog d;

    private EditText storeName,email,phoneNo,password,confirmPassword;
    Button buttonCreateAccount;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_signup);

        storeName = findViewById(R.id.store_name);
        email=findViewById(R.id.email);
        phoneNo=findViewById(R.id.phone_no);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirm_password);
        buttonCreateAccount=findViewById(R.id.createA);
        progressBar=findViewById(R.id.progressbar);

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
                    phoneNo.setError("Phone no should be 8 digits");
                    phoneNo.requestFocus();
                }else if(TextUtils.isEmpty(Store_Password)) {
                    password.setError("Password Required");
                    password.requestFocus();
                }else if(Store_Password.length()<3){
                        password.setError("Password should be at least 3 digits");
                        password.requestFocus();
                }
                else if(TextUtils.isEmpty(Store_ConfirmPassword)){
                    confirmPassword.setError("Confirm password is Required");
                    confirmPassword.requestFocus();
                }else if(!Store_ConfirmPassword.equals(Store_Password)){
                     confirmPassword.setError("Password Does Not Match");
                    confirmPassword.requestFocus();
                }else{
                    //If all the fields are filled and valid
                    //Toast.makeText(signup.this, "Account Created", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(Store_Name,Store_Email,Store_PhoneNo,Store_Password);

                }
            }
        });
    }

    private void registerUser(String store_name, String store_email, String store_phoneNo, String store_password) {
        //FirebaseAuth(): The entry point of the Firebase Authentication SDK, obtain an instance of this class by calling getinstance().
        // Then sign up or sign in or register a user with one of the methods
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //1.Create user profile
        auth.createUserWithEmailAndPassword(store_email,store_password).addOnCompleteListener(OwnerSignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    //2.Entering user data into realtime database : Firebase
                    ReadWriteOwnerDetails WriteUserDetails = new ReadWriteOwnerDetails(store_name,store_email,store_phoneNo,store_password);

                    //Extracting user reference from Database for "Registered Users"
                    //To read or write data from database you need an instance of DatabaseReference
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                    referenceProfile.child(firebaseUser.getUid()).setValue(WriteUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //when value of data is successfully stored in database
                           if(task.isSuccessful()){
                               //send email verification
                               firebaseUser.sendEmailVerification();
                               progressBar.setVisibility(View.GONE);
                               openALERT_Dialog("Registration successful","Please verify your email before logging in.");

                           }else{
                               progressBar.setVisibility(View.GONE);
                               Toast.makeText(OwnerSignUp.this, "Registration Failed .Please Check Network", Toast.LENGTH_SHORT).show();
                           }
                        }
                    });

                }else{
                    progressBar.setVisibility(View.GONE);
                    openALERT_Dialog("Sign in failed",task.getException().getMessage());
                }
            }
        });
    }

    private void openALERT_Dialog(String Atitle,String Amessage) {
        d=new Dialog(this);
        d.setContentView(R.layout.alert_dialog);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().getAttributes().windowAnimations = R.style.animation;
        TextView title = d.findViewById(R.id.alertMessageTitle);
        TextView message = d.findViewById(R.id.alertMessage);
        title.setText(Atitle);
        message.setText(Amessage);

        ImageView imageViewClose = d.findViewById(R.id.imageViewClose);
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        Button ok = d.findViewById(R.id.okBTN);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }
}

