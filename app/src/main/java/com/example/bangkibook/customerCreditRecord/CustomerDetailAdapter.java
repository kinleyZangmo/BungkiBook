package com.example.bangkibook.customerCreditRecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bangkibook.R;
import java.util.ArrayList;


public class CustomerDetailAdapter extends RecyclerView.Adapter<CustomerDetailAdapter.myViewHolder>  {

    Context context;
    ArrayList<CustomerCreditDetails> list;

    public CustomerDetailAdapter(Context context, ArrayList<CustomerCreditDetails> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.credit_details, parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerDetailAdapter.myViewHolder holder, int position) {
        CustomerCreditDetails customerCreditDetails = list.get(position);
        holder.date.setText(customerCreditDetails.getDate());
        holder.amount.setText(String.valueOf(customerCreditDetails.getAmount()));
        holder.remark.setText(customerCreditDetails.getRemark());
        holder.clearOrAdd.setText(customerCreditDetails.getAddOrClear());
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public int getItemViewType(int position){
        return position;
    }


    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView date, amount, remark, clearOrAdd;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
            remark = itemView.findViewById(R.id.remark);
            clearOrAdd = itemView.findViewById(R.id.clearOrAdd);
        }
    }
}
