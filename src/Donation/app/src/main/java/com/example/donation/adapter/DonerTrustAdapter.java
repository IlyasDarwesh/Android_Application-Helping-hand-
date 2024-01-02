package com.example.donation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donation.R;
import com.example.donation.constructor.TrustConstructor;
import com.example.donation.doner.Pay;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class DonerTrustAdapter extends FirebaseRecyclerAdapter<TrustConstructor, DonerTrustAdapter.myviewHolder> {
    private Context context;
    public DonerTrustAdapter(@NonNull FirebaseRecyclerOptions<TrustConstructor> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewHolder holder, int position, @NonNull TrustConstructor model) {
        holder.name.setText(model.getName());
        holder.desc.setText(model.getEmail());
        holder.btn_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, Pay.class);
                i.putExtra("trustName", model.getName());
                i.putExtra("trustEmail", model.getEmail());
                context.startActivity(i);
            }
        });
    }



    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_doner_trust, parent,false);
        context = parent.getContext();
        return new myviewHolder(view);
    }

    class myviewHolder extends RecyclerView.ViewHolder{
        TextView name,desc;
        Button btn_donate;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_trust_name);
            desc = itemView.findViewById(R.id.tv_trust_desc);

            btn_donate = itemView.findViewById(R.id.btn_trust_donate);
        }
    }

}
