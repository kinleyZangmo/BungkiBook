package com.example.bangkibook.storeOwner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bangkibook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OwnerProfile extends AppCompatActivity {
    TextView name,email,phoneNo;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);
        name=findViewById(R.id.storeName);
        email=findViewById(R.id.storeEmail);
        phoneNo=findViewById(R.id.storePhoneNo);
        read();
    }

    public void read(){
        Intent i = getIntent();
        String uid = i.getStringExtra("uid");
        //root node Registered User
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
        //child node of root node, The Store Owners
        databaseReference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        Toast.makeText(getApplicationContext(),"Sucessfully Read",Toast.LENGTH_LONG).show();
                        DataSnapshot dataSnapshot =task.getResult();
                        name.setText(dataSnapshot.child("Store_name").getValue().toString());
                        email.setText(dataSnapshot.child("Store_email").getValue().toString());
                        phoneNo.setText(dataSnapshot.child("Store_phoneNo").getValue().toString());
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Failed to read",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Logout Session
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Logged Out", Toast.LENGTH_LONG).show();
        Intent logout = new Intent(this, OwnerLogin.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logout);
    }


}