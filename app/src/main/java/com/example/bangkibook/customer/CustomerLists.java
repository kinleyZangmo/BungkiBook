package com.example.bangkibook.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bangkibook.Model;
import com.example.bangkibook.R;
import com.example.bangkibook.myadapter;
import com.example.bangkibook.storeOwner.OwnerProfile;

import java.util.ArrayList;

public class CustomerLists extends AppCompatActivity {

    RecyclerView rcv;
    myadapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        rcv = (RecyclerView) findViewById(R.id.recview);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new myadapter(dataqueue());
        rcv.setAdapter(adapter);
    }
        public ArrayList<Model> dataqueue() {
        ArrayList<Model> holder = new ArrayList<>();
        String[] name =new String[] {"Kinley", "Kezang", "Dechen","Kinley", "Kezang", "Dechen","Kinley", "Kezang", "Dechen","Kinley", "Kezang", "Dechen"};
        int[] stdId =new int[] {02200150, 02200152,02200144,02200150, 02200152,02200144,02200150, 02200152,02200144,02200150, 02200152,02200144};

        int len = name.length;
        Model[] arr =  new Model[len];

        for (int i = 0; i < len; i++) {
            arr[i] = new Model();
            arr[i].setHeader(name[i]);
            arr[i].setDesc(String.valueOf(stdId[i]));
            holder.add(arr[i]);
        }
        return holder;
    }

    public void addCustomer(View view) {
        Intent intentAddCustomer = new Intent(this, CustomerAdd.class);
        startActivity(intentAddCustomer);
    }

    public void viewCustomerCredit(View view) {
        Intent credit = new Intent(this,CustomerCredit.class);
        startActivity(credit);
    }

    public void DisplayProfile(View view) {
        Intent credit = new Intent(this, OwnerProfile.class);
        startActivity(credit);
    }
}