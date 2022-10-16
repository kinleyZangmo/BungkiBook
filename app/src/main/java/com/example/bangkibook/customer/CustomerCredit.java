package com.example.bangkibook.customer;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bangkibook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerCredit extends AppCompatActivity {
    Dialog dialog,dialog2;
    String stdId,uid;
    private  TextView name, amount;

    ArrayList<CustomerInfo> list =new ArrayList<>();
    private Object DataSnapshot;
    public String nameV,amountV,emailV,phoneNoV;

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

                    nameV = String.valueOf(dataSnapshot.child("name").getValue());
                    amountV = String.valueOf(dataSnapshot.child("credit").getValue());

                    //retrieving for customer full profile too, here -kz
                    emailV=String.valueOf(dataSnapshot.child("email").getValue());
                    phoneNoV=String.valueOf(dataSnapshot.child("phoneNumber").getValue());


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
                openAddDialog();
            }
        });
        //CLEARING CUSTOMER CREDIT
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClearDialog();
            }
        });

    }

    //credit details
//1. Date
//2. Amount
//3. Remarks
//4. Status(clear/add)

    //Dialog Box
    private void openAddDialog() {
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.add_credit_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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

                 //[1.Date] ??   String f= String.valueOf(LocalDate.now());
                int credit_amount=Integer.parseInt(addC.getText().toString()); //[2.Amount]
                String credit_remark=remark.getText().toString(); // [3. Remarks]
                String credit_status="add"; //[4.Status]
                Toast.makeText(getApplicationContext(),"CREDIT ADDED",Toast.LENGTH_SHORT).show();
                // YOUR CODE PROBABLY SHOULD CONTINUE FROM HERE
                //ADD credit to database here

                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void openClearDialog() {
        dialog2=new Dialog(this);
        dialog2.setContentView(R.layout.add_credit_dialog);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
            public void onClick(View v) {
               // remark.setText("");
                dialog.dismiss();

            }
        });

        //final clear credit/confirm button
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //??DATE   String f= String.valueOf(LocalDate.now()); [1.Date]
                int credit_amount=Integer.parseInt(clearC.getText().toString()); //[2.Amount]
                String credit_remark=remark.getText().toString();  // [3. Remarks]
                String credit_status="clear"; //[4.Status]

                Toast.makeText(getApplicationContext(),"CREDIT CLEARED",Toast.LENGTH_SHORT).show();
                dialog2.dismiss();

                // YOUR CODE PROBABLY SHOULD CONTINUE FROM HERE
                //CLEAR credit from database here

            }
        });

        dialog2.show();
    }

    public void DisplayCustomerProfile(View view) {
        Intent customerProfile = new Intent(this,CustomerProfile.class);

        customerProfile.putExtra("cSid",stdId);
        customerProfile.putExtra("cName",nameV);
        customerProfile.putExtra("cEmail",emailV);
        customerProfile.putExtra("cPhoneNo",phoneNoV);
        startActivity(customerProfile);
    }
}

