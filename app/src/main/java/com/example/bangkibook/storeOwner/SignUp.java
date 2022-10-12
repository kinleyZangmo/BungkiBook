package com.example.bangkibook.storeOwner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bangkibook.R;
import com.example.bangkibook.ReadWriteUserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private EditText storeName,email,phoneNo,password,confirmPassword;
    Button buttonCreateAccount;
    ProgressBar progressBar;

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
        auth.createUserWithEmailAndPassword(store_email,store_password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    //2.Entering user data into realtime database : Firebase
                    ReadWriteUserDetails WriteUserDetails = new ReadWriteUserDetails(store_name,store_email,store_phoneNo,store_password);

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
                               Toast.makeText(SignUp.this, "User Registered Successfully. Please verify your email and login", Toast.LENGTH_LONG).show();

                               //Open Login Activity (our main page)
                               Intent registered = new Intent(getApplicationContext(), MainActivity.class);

                               //removing previous activities to avoid backstack
                               //To prevent user from returning back to sign up activity on pressing back button after signup
                              // registered.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                               startActivity(registered);
                               finish(); //to close signup activity
                           }else{
                               progressBar.setVisibility(View.GONE);
                               Toast.makeText(SignUp.this, "Registration Failed .Please Check Network", Toast.LENGTH_SHORT).show();

                           }
                        }
                    });

                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUp.this, "User Registration failed. Please try again", Toast.LENGTH_SHORT).show();
                }
//                progressBar.setVisibility(View.GONE);
//                Toast.makeText(signup.this, "ONLY REGISTERED", Toast.LENGTH_SHORT).show();
//                Intent registered = new Intent(getApplicationContext(), Customer_Main.class);
//                startActivity(registered);
            }
        });
    }

}
// 1.Create user
// 2.Save user data into database
// 3.