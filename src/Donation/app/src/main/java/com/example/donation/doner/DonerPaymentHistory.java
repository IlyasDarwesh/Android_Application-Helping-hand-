package com.example.donation.doner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.donation.AuthConstructor;
import com.example.donation.R;
import com.example.donation.adapter.DonerPaymentAdapter;
import com.example.donation.adapter.DonerTrustAdapter;
import com.example.donation.constructor.PaymentConstructor;
import com.example.donation.constructor.TrustConstructor;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DonerPaymentHistory extends AppCompatActivity {
    RecyclerView recyclerView;
    DonerPaymentAdapter adapter;
    FirebaseAuth auth;
    String get_user_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doner_payment_history);

        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.rv_doner_payment_history);
        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        mFirebaseDatabaseReference.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AuthConstructor user = snapshot.getValue(AuthConstructor.class);
                get_user_email = user.getEmail();

                Toast.makeText(getApplicationContext(), ""+get_user_email, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DonerPaymentHistory.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        FirebaseRecyclerOptions<PaymentConstructor> options = new FirebaseRecyclerOptions.Builder<PaymentConstructor>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Donation").orderByChild("sender_email").equalTo("saadashraf721@gmail.com"), PaymentConstructor.class)
                .build();

        adapter = new DonerPaymentAdapter(options);
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