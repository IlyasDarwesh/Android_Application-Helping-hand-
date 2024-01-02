package com.example.donation.doner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donation.Login;
import com.example.donation.R;
import com.example.donation.adapter.AdminCompaignAdapter;
import com.example.donation.adapter.DonerTrustAdapter;
import com.example.donation.constructor.CompaignConstructor;
import com.example.donation.constructor.TrustConstructor;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DonerHome extends AppCompatActivity {

    RecyclerView recyclerView;
    AdminCompaignAdapter adapter;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    AlertDialog.Builder builder;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    CardView btn_donate,btn_ngo,btn_compaign,btn_cash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doner_home);

        builder = new AlertDialog.Builder(this);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationDrawer();

        recyclerView = findViewById(R.id.rv_compaign);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        FirebaseRecyclerOptions<CompaignConstructor> options = new FirebaseRecyclerOptions.Builder<CompaignConstructor>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("compaign"), CompaignConstructor.class)
                .build();

        adapter = new AdminCompaignAdapter(options);
        recyclerView.setAdapter(adapter);

        btn_donate = findViewById(R.id.cd_btn_donate);
        btn_compaign = findViewById(R.id.cd_btn_compaign);
        btn_cash = findViewById(R.id.cd_btn_history);
        btn_ngo = findViewById(R.id.cd_btn_ngo);

        btn_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),DonerTrustList.class);
                startActivity(i);
            }
        });
        btn_ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),DonerPredict.class);
                startActivity(i);
            }
        });
        btn_compaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),DonerCompaignList.class);
                startActivity(i);
            }
        });
        btn_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),DonerPaymentHistory.class);
                startActivity(i);
            }
        });

    }

    private void setNavigationDrawer() {
        drawerLayout = findViewById(R.id.my_drawer_layout); // initiate a DrawerLayout
        NavigationView navView = (NavigationView) findViewById(R.id.navigation); // initiate a Navigation View
        View headerView = navView.getHeaderView(0);
        TextView nav_name = headerView.findViewById(R.id.nav_header_name);
        TextView nav_email = headerView.findViewById(R.id.nav_header_email);

        DatabaseReference firebaseDatabase;
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mynames =snapshot.child("name").getValue().toString();
                String myemails =snapshot.child("email").getValue().toString();
                if (!mynames.isEmpty()){
                    nav_name.setText(mynames);
                    nav_email.setText(myemails);
                }else {
                    Toast.makeText(DonerHome.this, "no", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId(); // get selected menu item's id
                if (itemId == R.id.nav_donate) {
                    Intent i = new Intent(getApplicationContext(), DonerTrustList.class);
                    startActivity(i);
                }if (itemId == R.id.nav_prediction){
                    Intent i = new Intent(getApplicationContext(), DonerPredict.class);
                    startActivity(i);
                }if (itemId == R.id.nav_history){
                    Intent i = new Intent(getApplicationContext(), DonerPaymentHistory.class);
                    startActivity(i);
                }if (itemId == R.id.nav_profile){
                    Intent i = new Intent(getApplicationContext(), DonerProfile.class);
                    startActivity(i);
                }if (itemId == R.id.nav_compaign){
                    Intent i = new Intent(getApplicationContext(), DonerCompaignList.class);
                    startActivity(i);
                }if (itemId == R.id.nav_logout){
                    drawerLayout.closeDrawers();
                    builder.setMessage("ok").setTitle("oks");
                    builder.setMessage("Do you want to logout ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    FirebaseAuth.getInstance().signOut();
                                    Intent a = new Intent(getApplicationContext(), Login.class);
                                    startActivity(a);
                                    finish();
                                    Toast.makeText(DonerHome.this, "Logout:success", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Logout Cancel",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Alert!");
                    alert.show();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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