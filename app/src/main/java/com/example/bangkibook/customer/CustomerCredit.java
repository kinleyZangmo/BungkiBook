package com.example.bangkibook.customer;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bangkibook.R;
import com.example.bangkibook.customerCreditRecord.CustomerCreditDetails;
import com.example.bangkibook.customerCreditRecord.CustomerDetailAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CustomerCredit extends AppCompatActivity {
    Dialog dialog,dialog2;
    String stdId,uid;
    private  TextView name, amount;

    RecyclerView recyclerView;
    CustomerDetailAdapter customerDetailAdapter;
    ArrayList<CustomerCreditDetails> list = new ArrayList<>();

    public String nameV,amountV,emailV,phoneNoV,totalCreditV;
    EditText addC,clearC;
    Button add_btn,clear_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_credit);
        addC=findViewById(R.id.addCreditTxt);
        clearC=findViewById(R.id.clearCreditTxt);
        add_btn=findViewById(R.id.addbtn);
        clear_btn=findViewById(R.id.clearbtn);

        //RETRIEVING Customer details
        Intent i = getIntent();
        uid = i.getStringExtra("uid");
        stdId = i.getStringExtra("stdId");

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

                    nameV = String.valueOf(dataSnapshot.child("name").getValue());
                    amountV = String.valueOf(dataSnapshot.child("credit").getValue());

                    //retrieving for customer full profile too, here -kz
                    emailV=String.valueOf(dataSnapshot.child("email").getValue());
                    phoneNoV=String.valueOf(dataSnapshot.child("phoneNumber").getValue());
                    totalCreditV=String.valueOf(dataSnapshot.child("credit").getValue());

                    name.setText(nameV);
                    amount.setText("Nu."+amountV);

                } else{
                    Toast.makeText(CustomerCredit.this, "Get to get data ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //ADDING CREDIT
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(addC.getText().toString().isEmpty()){
                     addC.requestFocus();
                     addC.setError("Input required");
                }else if(Integer.parseInt(addC.getText().toString())==0){
                    addC.requestFocus();
                    addC.setError("Cannot add credit Nu.0");
                }else{
                    openAddDialog();
                }
            }
        });
        //CLEARING CUSTOMER CREDIT
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clearC.getText().toString().isEmpty()){
                    clearC.requestFocus();
                    clearC.setError("Input required");
                }else if(Integer.parseInt(clearC.getText().toString())==0){
                    clearC.requestFocus();
                    clearC.setError("Cannot clear credit Nu.0");
                }else if(Integer.parseInt(clearC.getText().toString())>Integer.parseInt(amountV)){
                    clearC.requestFocus();
                    clearC.setError("Error. Credit balance will be negative");
                }
                else{
                    openClearDialog();
                }

            }
        });

        //displaying the credit details in tabular form using recycle view
        recyclerView = findViewById(R.id.credit_details);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        customerDetailAdapter = new CustomerDetailAdapter(this, list);
        recyclerView.setAdapter(customerDetailAdapter);
        root.child(stdId).child("creditDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CustomerCreditDetails customerCreditDetails = dataSnapshot.getValue(CustomerCreditDetails.class);
                    list.add(customerCreditDetails);
                }
                customerDetailAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //credit details
//1. Date
//2. Amount
//3. Remarks
//4. Status(clear/add)

//ADDING CREDIT HERE
    private void openAddDialog() {
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.add_credit_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        Button btn_ok= dialog.findViewById(R.id.okBTN);
        Button btn_delete=dialog.findViewById(R.id.deleteBTN);
        EditText remark = dialog.findViewById(R.id.remark_txt);
        TextView head =dialog.findViewById(R.id.displayCredit);
        head.setText("Are you sure you want to add credit Nu."+addC.getText().toString()+" ?");

        //closing the dialog box
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //Cancel
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // remark.setText("");
                dialog.dismiss();
            }
        });

        //final add/confirm button
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());

                int credit_amount=Integer.parseInt(addC.getText().toString()); //[2.Amount]
                String credit_remark=remark.getText().toString(); // [3. Remarks]
                String credit_status="add"; //[4.Status]

                CustomerCreditDetails customerCreditDetails = new CustomerCreditDetails(date,credit_amount,credit_remark,credit_status);
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference root = db.getReference().child("Registered Users").child(uid).child("customers");
                root.child(stdId).child("creditDetails").child(currentDateandTime).setValue(customerCreditDetails);

                int updatedAmount = Integer.parseInt(amountV) + credit_amount;
                root.child(stdId).child("credit").setValue(updatedAmount);

                Toast.makeText(getApplicationContext(),"CREDIT ADDED",Toast.LENGTH_SHORT).show();

                dialog.dismiss();
                finish();
                overridePendingTransition(0,0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
        dialog.show();
    }

//CLEARING CREDIT HERE
    private void openClearDialog() {
        dialog2=new Dialog(this);
        dialog2.setContentView(R.layout.add_credit_dialog);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.getWindow().getAttributes().windowAnimations = R.style.animation;

        ImageView imageViewClose = dialog2.findViewById(R.id.imageViewClose);
        Button btn_ok = dialog2.findViewById(R.id.okBTN);
        Button btn_delete=dialog2.findViewById(R.id.deleteBTN);
        EditText remark = dialog2.findViewById(R.id.remark_txt);
        TextView head =dialog2.findViewById(R.id.displayCredit);
        head.setText("Are you sure you want to clear credit Nu."+clearC.getText().toString()+" ?");

        //closing the dialog box
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        //Cancel
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dialog2.dismiss();}
        });

        //final clear credit/confirm button
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm a");
                String currentDateandTime = sdf.format(new Date()); //[1.Date]

                int credit_amount= Integer.parseInt(clearC.getText().toString()); //[2.Amount]
                String credit_remark= remark.getText().toString();  // [3. Remarks]
                String credit_status="clear"; //[4.Status]

                CustomerCreditDetails customerCreditDetails = new CustomerCreditDetails(date,credit_amount,credit_remark,credit_status);
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference root = db.getReference().child("Registered Users").child(uid).child("customers");
                root.child(stdId).child("creditDetails").child(currentDateandTime).setValue(customerCreditDetails);

                int updatedAmount = Integer.parseInt(amountV) - credit_amount;
                root.child(stdId).child("credit").setValue(updatedAmount);

                Toast.makeText(getApplicationContext(),"CREDIT CLEARED",Toast.LENGTH_SHORT).show();

                dialog2.dismiss();
                finish();
                overridePendingTransition(0,0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
        dialog2.show();
    }

    public void DisplayCustomerProfile(View view) {
        Intent i = getIntent();
        String uid = i.getStringExtra("uid");

        Intent customerProfile = new Intent(this, CustomerProfile.class);
        customerProfile.putExtra("cSid",stdId);
        customerProfile.putExtra("cName",nameV);
        customerProfile.putExtra("cEmail",emailV);
        customerProfile.putExtra("cPhoneNo",phoneNoV);
        customerProfile.putExtra("cCredit",totalCreditV);

        startActivity(customerProfile);
    }
}

