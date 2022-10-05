package com.example.bangkibook;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
   private EditText EditTxtemail, EditTxtpassword;
   private ProgressBar progressBar;
   private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditTxtemail=findViewById(R.id.user_email);
        EditTxtpassword=findViewById(R.id.user_password);
        progressBar=findViewById(R.id.progressbar);

        authProfile = FirebaseAuth.getInstance();
    }

    public void loginClick(View view) {
        String txtEmail = EditTxtemail.getText().toString();
        String txtPassword = EditTxtpassword.getText().toString();

        if(TextUtils.isEmpty(txtEmail)){
            EditTxtemail.setError("Email is Required");
            EditTxtemail.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()){
            EditTxtemail.setError("Valid is Required");
            EditTxtemail.requestFocus();
        }
        else if(TextUtils.isEmpty(txtPassword)){
            EditTxtpassword.setError("Password is Required");
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
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent intentLogin = new Intent(getApplicationContext(), Customer_Main.class);
                    startActivity(intentLogin);
                }else{
                    Toast.makeText(MainActivity.this, "Login failed, try again", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void signupClick(View view) {

        Intent intentSignup = new Intent(this, signup.class);
        startActivity(intentSignup);
        // intent takes to signup activity
    }


}