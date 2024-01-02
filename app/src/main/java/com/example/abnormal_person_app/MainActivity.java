package com.example.abnormal_person_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {
   Button btn_login ;
   Button btn_register;
   TextView password_forget;

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
        setContentView(R.layout.activity_main);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        password_forget = findViewById(R.id.password_forget);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Login.class);
                startActivity(intent);

            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);

            }
        });
        password_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Forget_Password.class);
                startActivity(intent);

            }
        });

    }
}