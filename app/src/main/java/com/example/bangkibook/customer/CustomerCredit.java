package com.example.bangkibook.customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bangkibook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerCredit extends AppCompatActivity {
    String stdId,uid;
    private  TextView name, amount;

    ArrayList<CustomerInfo> list =new ArrayList<>();
    private Object DataSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_credit);

        Intent i = getIntent();
        uid = i.getStringExtra("uid");
        stdId = i.getStringExtra("stdId");
        System.out.println(uid + "helllo working " + stdId);


        name = findViewById(R.id.customerName);
        amount = findViewById(R.id.Amount);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference().child("Registered Users").child(uid).child("customers");

        root.child(stdId).get().addOnCompleteListener(new OnCompleteListener<com.google.firebase.database.DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<com.google.firebase.database.DataSnapshot> task) {

                if(task.isSuccessful()) {
                    Toast.makeText(CustomerCredit.this, "data Successfully read", Toast.LENGTH_SHORT).show();
                    DataSnapshot dataSnapshot = task.getResult();

                    String nameV = String.valueOf(dataSnapshot.child("name").getValue());
                    String amountV = String.valueOf(dataSnapshot.child("credit").getValue());

                    name.setText(nameV);
                    amount.setText(amountV);

                } else{
                    Toast.makeText(CustomerCredit.this, "Get to get data ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}