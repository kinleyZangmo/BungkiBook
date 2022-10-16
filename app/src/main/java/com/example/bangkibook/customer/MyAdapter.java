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
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder>  {

    Context context;
    ArrayList<CustomerInfo> list;
    private final OnNoteListener mOnNoteListener;
    public MyAdapter(Context context, ArrayList<CustomerInfo> list, OnNoteListener onNoteListener) {
        this.context = context;
        this.list = list;
        this.mOnNoteListener = onNoteListener;
    }

    //TESTING
    public void setFilteredList(ArrayList<CustomerInfo> filteredList ){
        this.list =filteredList;
        notifyDataSetChanged();

    }
    //TESTING

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.singlerow, parent, false);
        return new myViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        CustomerInfo customerInfo = list.get(position);
        holder.name.setText(customerInfo.getName());
        holder.sid.setText(customerInfo.getStdId());

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, sid;
        OnNoteListener onNoteListener;

        public myViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            sid = itemView.findViewById(R.id.stdId);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
