package com.example.abnormal_person_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity {
Button button;
Button button1;
Button button2;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;


    @Override
    public void onBackPressed() {
        // Disable the back button by not calling super.onBackPressed()
        // You can add any custom logic here if needed
        // For example, show a message to the user or do nothing
        // To re-enable the back button, simply remove this method override
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        mAuth=FirebaseAuth.getInstance();
        button=findViewById(R.id.searchdata_button);
        button1=findViewById(R.id.filldata_button);
        button2=findViewById(R.id.Logout);


        firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser==null){
            Intent intent=new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getApplicationContext(),Fetch_image.class);
        startActivity(intent);
        finish();

    }
});
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Add_data_Form.class);
                startActivity(intent);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(Dashboard.this, "Account Logout", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}