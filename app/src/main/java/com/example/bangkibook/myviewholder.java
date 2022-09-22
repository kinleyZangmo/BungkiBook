package com.example.bangkibook;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bangkibook.R;

public class myviewholder extends RecyclerView.ViewHolder
{
    TextView t1,t2;
    public myviewholder(@NonNull View itemView)
    {
        super(itemView);
        t1=(TextView)itemView.findViewById(R.id.t1);
        t2=(TextView)itemView.findViewById(R.id.t2);
    }
}

