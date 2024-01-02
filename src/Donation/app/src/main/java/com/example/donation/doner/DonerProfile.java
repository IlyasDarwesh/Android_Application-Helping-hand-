package com.example.donation.doner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donation.AuthConstructor;
import com.example.donation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.bloco.faker.Faker;

public class DonerProfile extends AppCompatActivity {
    TextView set_name,set_email,set_mobile,set_address;
    Faker faker;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doner_profile);

        faker = new Faker();
        auth = FirebaseAuth.getInstance();

        set_name = findViewById(R.id.name_p);
        set_email = findViewById(R.id.email_p);
        set_address = findViewById(R.id.address_p);
        set_mobile = findViewById(R.id.number_p);


        set_address.setText(faker.address.streetAddress());
        set_mobile.setText("0333-"+faker.number.number(7));

        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        mFirebaseDatabaseReference.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AuthConstructor user = snapshot.getValue(AuthConstructor.class);
                String get_user_name = user.getName();
                String get_user_email = user.getEmail();

                set_name.setText(get_user_name);
                set_email.setText(get_user_email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DonerProfile.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}