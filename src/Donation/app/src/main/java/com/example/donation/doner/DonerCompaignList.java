package com.example.donation.doner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.donation.R;
import com.example.donation.adapter.AdminCompaignAdapter;
import com.example.donation.adapter.DonerCompaignAdapter;
import com.example.donation.adapter.DonerTrustAdapter;
import com.example.donation.constructor.CompaignConstructor;
import com.example.donation.constructor.TrustConstructor;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DonerCompaignList extends AppCompatActivity {
    RecyclerView recyclerView;
    DonerCompaignAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doner_compaign_list);

        recyclerView = findViewById(R.id.rv_doner_compaign);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<CompaignConstructor> options = new FirebaseRecyclerOptions.Builder<CompaignConstructor>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("compaign"), CompaignConstructor.class)
                .build();

        adapter = new DonerCompaignAdapter(options);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}