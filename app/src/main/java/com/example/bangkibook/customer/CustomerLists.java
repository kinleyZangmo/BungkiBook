package com.example.bangkibook.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

public class CustomerLists extends AppCompatActivity implements MyAdapter.CustomerClickListener {
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<CustomerInfo> list =new ArrayList<>();
    SearchView searchView;

    String uid;
    private final FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        Intent i = getIntent();
        uid = i.getStringExtra("uid");

        recyclerView = findViewById(R.id.userlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(list,this,this);
        recyclerView.setAdapter(myAdapter);

        //RETRIEVING CUSTOMER LIST DATA FROM DATABASE
        DatabaseReference root = db.getReference().child("Registered Users").child(uid).child("customers");
        root.addListenerForSingleValueEvent(new ValueEventListener() {
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
                Toast.makeText(getApplicationContext(),error.getMessage().toString(),Toast.LENGTH_LONG);
            }
        });


        //SEARCH FUNCTION (name or student id )
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;}
        });
    }

    //SEARCH FILTER
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
            Toast.makeText(this, "Customer Not Found", Toast.LENGTH_SHORT).show();
        }else{
            myAdapter.setFilteredList(filterList);
        }
    }

    @Override
    public void selectedCustomer(CustomerInfo customerInfo) {
        Intent intent = new Intent(CustomerLists.this,CustomerCredit.class);
        intent.putExtra("uid", uid);
        intent.putExtra("stdId", customerInfo.getStdId());
        startActivity(intent);
    }

    //ADDING NEW CUSTOMER
    public void addCustomer(View view) {
        Intent intentAddCustomer = new Intent(this, CustomerAdd.class);
        intentAddCustomer.putExtra("uid", uid);
        startActivity(intentAddCustomer);
    }

    //DISPLAYING OWNER PROFILE
    public void DisplayProfile(View view) {
        Intent c = new Intent(this, OwnerProfile.class);
        c.putExtra("uid", uid);
        startActivity(c);
    }
}