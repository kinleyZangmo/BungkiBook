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

import com.example.bangkibook.customer.CustomerLists;
import com.example.bangkibook.R;
import com.example.bangkibook.customerCreditRecord.CustomerCreditDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OwnerLogin extends AppCompatActivity {
    Dialog d;
    private EditText EditTxtemail, EditTxtpassword;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);

        EditTxtemail=findViewById(R.id.user_email);
        EditTxtpassword=findViewById(R.id.user_password);
        progressBar=findViewById(R.id.progressbar);
        authProfile = FirebaseAuth.getInstance();
    }

    public void loginClick(View view) {
        String txtEmail = EditTxtemail.getText().toString();
        String txtPassword = EditTxtpassword.getText().toString();

        if(TextUtils.isEmpty(txtEmail)){
            EditTxtemail.setError("Email is required");
            EditTxtemail.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()){
            EditTxtemail.setError("Valid email is required");
            EditTxtemail.requestFocus();
        }
        else if(TextUtils.isEmpty(txtPassword)){
            EditTxtpassword.setError("Password is required");
            EditTxtpassword.requestFocus();
        }else {
            progressBar.setVisibility(View.VISIBLE);
            loginUser(txtEmail,txtPassword);

        }
    }

    private void loginUser(String txtEmail, String txtPassword) {
        //login using email and password

            authProfile.signInWithEmailAndPassword(txtEmail,txtPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    FirebaseUser firebaseUser =authProfile.getCurrentUser();
                    if(!firebaseUser.isEmailVerified()){
                        progressBar.setVisibility(View.GONE);
                        emailNotV_dialog();
                    }else {
                        if (task.isSuccessful()) {
                            Toast.makeText(OwnerLogin.this, "You are logged in", Toast.LENGTH_SHORT).show();
                            Intent intentLogin = new Intent(getApplicationContext(), CustomerLists.class);
                            intentLogin.putExtra("uid", authProfile.getUid());
                            startActivity(intentLogin);
                        } else {
                            Toast.makeText(OwnerLogin.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }

    public void signupClick(View view) {
        Intent intentSignup = new Intent(this, OwnerSignUp.class);
        startActivity(intentSignup);
        // intent takes to signup activity
    }

    private void emailNotV_dialog() {
        d=new Dialog(this);
        d.setContentView(R.layout.alert_dialog);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().getAttributes().windowAnimations = R.style.animation;
        TextView message = d.findViewById(R.id.alertMessage);
        message.setText(R.string.emailNotVerified);

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


