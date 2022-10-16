package com.example.bangkibook.customer;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bangkibook.R;
import com.example.bangkibook.storeOwner.OwnerProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerLists extends AppCompatActivity implements MyAdapter.OnNoteListener{
    SearchView searchView;
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

        //SEARCH FUNCTION
      searchView = findViewById(R.id.searchView);
      searchView.clearFocus();
      searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String query) {
              return false;
          }

          @Override
          public boolean onQueryTextChange(String newText) {
              filterList(newText);
              return true;
          }
      });
    }

    private void filterList(String newText) {
        ArrayList<CustomerInfo> filterList = new ArrayList<>();
        for(CustomerInfo customerInfo : list){
            if(customerInfo.getName().toLowerCase().contains(newText.toLowerCase())){
                filterList.add(customerInfo);
            }else if(customerInfo.getStdId().contains(newText)){
                filterList.add(customerInfo);
            }
        }
        if(filterList.isEmpty()){
            Toast.makeText(this, "No customer found", Toast.LENGTH_SHORT).show();
        }else{
            myAdapter.setFilteredList(filterList);
        }
    }

    public void addCustomer(View view) {
        Intent intentAddCustomer = new Intent(this, CustomerAdd.class);
        intentAddCustomer.putExtra("uid", uid);
        startActivity(intentAddCustomer);
    }

    public void viewCustomerCredit(View view) {
        Intent credit = new Intent(this,CustomerCredit.class);
        startActivity(credit);
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