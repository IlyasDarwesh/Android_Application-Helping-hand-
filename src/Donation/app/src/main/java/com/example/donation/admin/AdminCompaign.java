package com.example.donation.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.donation.AuthConstructor;
import com.example.donation.R;
import com.example.donation.constructor.CompaignConstructor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCompaign extends AppCompatActivity {
    EditText get_title,get_description,get_target,get_start;
    Button btn_submit;
    String title,description,target,start;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("compaign");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_compaign);

        btn_submit = findViewById(R.id.btn_submit);

        get_title = findViewById(R.id.et_title);
        get_description = findViewById(R.id.et_description);
        get_target = findViewById(R.id.et_target);
        get_start = findViewById(R.id.et_date);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = get_title.getText().toString().trim();
                description = get_description.getText().toString().trim();
                target = get_target.getText().toString().trim();
                start = get_start.getText().toString().trim();
                try {
                    CompaignConstructor compaign = new CompaignConstructor(title,description,"0",target,start,"01/01/2022","0");
                    mDatabase.push().setValue(compaign);
                    Toast.makeText(AdminCompaign.this, "Campaign Successfully Created", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(AdminCompaign.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}