package com.example.bangkibook.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bangkibook.R;
import com.example.bangkibook.storeOwner.OwnerProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerLists extends AppCompatActivity implements MyAdapter.OnNoteListener{

    RecyclerView recyclerView;
    String uid;
    private final FirebaseDatabase db = FirebaseDatabase.getInstance();
    MyAdapter myAdapter;
    ArrayList<CustomerInfo> list =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        Intent i = getIntent();
        uid = i.getStringExtra("uid");
//        System.out.println(uid + "working");

        DatabaseReference root = db.getReference().child("Registered Users").child(uid).child("customers");
        recyclerView = findViewById(R.id.userlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(this, list, this);
        recyclerView.setAdapter(myAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CustomerInfo customerInfo = dataSnapshot.getValue(CustomerInfo.class);
                    list.add(customerInfo);
                }

                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addCustomer(View view) {
        Intent intentAddCustomer = new Intent(this, CustomerAdd.class);
        intentAddCustomer.putExtra("uid", uid);
        startActivity(intentAddCustomer);
    }

    public void DisplayProfile(View view) {
        Intent c = new Intent(this, OwnerProfile.class);
        c.putExtra("uid", uid);
        startActivity(c);
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(CustomerLists.this,CustomerCredit.class);

        intent.putExtra("uid", uid);
        intent.putExtra("stdId", list.get(position).getStdId());
        startActivity(intent);
    }

}