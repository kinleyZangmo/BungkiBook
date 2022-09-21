package com.example.bangkibook.Customer_Package;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.sql.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //    Button button;
    RecyclerView rcv;
    myadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        button =findViewById(R.id.btn);
        rcv = (RecyclerView) findViewById(R.id.recview);
        rcv.setLayoutManager(new LinearLayoutManager(this));
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    getData();
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

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
}