package com.example.donation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donation.R;
import com.example.donation.constructor.PaymentConstructor;
import com.example.donation.constructor.TrustConstructor;
import com.example.donation.doner.Pay;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class DonerPaymentAdapter extends FirebaseRecyclerAdapter<PaymentConstructor, DonerPaymentAdapter.myviewHolder> {
    private Context context;
    public DonerPaymentAdapter(@NonNull FirebaseRecyclerOptions<PaymentConstructor> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DonerPaymentAdapter.myviewHolder holder, int position, @NonNull PaymentConstructor model) {
            holder.name.setText(model.getReciver_name());
            holder.date.setText(model.getDate());
            holder.amount.setText(model.getAmount());
    }



    @NonNull
    @Override
    public DonerPaymentAdapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_payment_history, parent,false);
        context = parent.getContext();
        return new DonerPaymentAdapter.myviewHolder(view);
    }

    class myviewHolder extends RecyclerView.ViewHolder{
        TextView name,date,amount;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textview3);
            date = itemView.findViewById(R.id.textview4);
            amount = itemView.findViewById(R.id.textview5);
        }
    }

}