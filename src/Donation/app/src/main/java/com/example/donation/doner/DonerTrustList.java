package com.example.donation.doner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.donation.R;
import com.example.donation.adapter.DonerTrustAdapter;
import com.example.donation.constructor.TrustConstructor;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DonerTrustList extends AppCompatActivity {
    RecyclerView recyclerView;
    DonerTrustAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doner_trust_list);

        recyclerView = findViewById(R.id.rv_trust_list_doner);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<TrustConstructor> options = new FirebaseRecyclerOptions.Builder<TrustConstructor>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("users").orderByChild("role").equalTo("Trust"), TrustConstructor.class)
                .build();

        adapter = new DonerTrustAdapter(options);
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