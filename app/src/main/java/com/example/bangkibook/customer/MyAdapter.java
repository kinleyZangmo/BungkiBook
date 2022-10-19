package com.example.bangkibook.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bangkibook.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder>  {
    public ArrayList<CustomerInfo> customerInfoList;
    public Context context;
    public CustomerClickListener customerClickListener;

    public interface CustomerClickListener{
        void selectedCustomer(CustomerInfo customerInfo);
    }

    public MyAdapter(ArrayList<CustomerInfo> customerInfoList, Context context,CustomerClickListener customerClickListener) {
        this.customerInfoList = customerInfoList;
        this.context = context;
        this.customerClickListener=customerClickListener;
    }
    public void setFilteredList(ArrayList<CustomerInfo> filteredList ){
        this.customerInfoList =filteredList;
        notifyDataSetChanged();}

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.singlerow, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        CustomerInfo customerInfo = customerInfoList.get(position);
        holder.name.setText(customerInfo.getName());
        holder.sid.setText(customerInfo.getStdId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerClickListener.selectedCustomer(customerInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerInfoList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        private TextView name, sid;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            sid = itemView.findViewById(R.id.stdId);
        }
    }
}