package com.example.bangkibook.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bangkibook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerProfile extends AppCompatActivity {
    TextView name,sID,email,phoneNo;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        name=findViewById(R.id.c_name);
        sID=findViewById(R.id.c_sid);
        email=findViewById(R.id.c_email);
        phoneNo=findViewById(R.id.c_phoneNO);
        send=findViewById(R.id.sendButton);

        Intent i = getIntent();
        name.setText(i.getStringExtra("cName"));
        sID.setText(i.getStringExtra("cSid"));
        email.setText(i.getStringExtra("cEmail"));
        phoneNo.setText(i.getStringExtra("cPhoneNo"));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TotalCredit=i.getStringExtra("cCredit");
                String customer_name=name.getText().toString();


                String[] address = email.getText().toString().split(",");
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL,address);
                intent.putExtra(Intent.EXTRA_SUBJECT,"CUSTOMER CREDIT");
                intent.putExtra(Intent.EXTRA_TEXT,"Dear "+customer_name+","+"\n"+"your total credit is Nu."+TotalCredit+".\n"+"Please clear it soon.");
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"No app installed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}