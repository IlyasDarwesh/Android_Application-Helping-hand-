package com.example.donation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donation.R;
import com.example.donation.constructor.CompaignConstructor;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class DonerCompaignAdapter extends FirebaseRecyclerAdapter<CompaignConstructor, DonerCompaignAdapter.myviewHolder> {
    private Context context;
    public DonerCompaignAdapter(@NonNull FirebaseRecyclerOptions<CompaignConstructor> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewHolder holder, int position, @NonNull CompaignConstructor model) {
        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        holder.target.setText(model.getTarget());
    }

    @NonNull
    @Override
    public DonerCompaignAdapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_compaign, parent,false);
        context = parent.getContext();
        return new DonerCompaignAdapter.myviewHolder(view);
    }

    class myviewHolder extends RecyclerView.ViewHolder{
        TextView title,desc,target;
        Button btn_donate;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            desc = itemView.findViewById(R.id.tv_description);
            target = itemView.findViewById(R.id.tv_target);
        }
    }

}
