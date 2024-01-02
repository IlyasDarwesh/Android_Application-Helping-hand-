package com.example.donation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText get_name, get_email, get_password;
    Spinner get_role;
    Button btn_register;
    String my_name, my_email, my_password, is_role;
    ProgressDialog dialog;

    FirebaseAuth auth;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        get_role = findViewById(R.id.is_Role);
        get_name = findViewById(R.id.et_signup_name);
        get_email = findViewById(R.id.et_signup_email);
        get_password = findViewById(R.id.et_signup_password);
        btn_register = findViewById(R.id.btn_register);

        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("loading...");
        dialog.setCancelable(false);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                is_role = get_role.getSelectedItem().toString().trim();
                my_name = get_name.getText().toString().trim();
                my_email = get_email.getText().toString().trim();
                my_password = get_password.getText().toString().trim();
                validation(my_name, my_email, my_password, is_role);
            }
        });
    }

    private void validation(String my_name, String my_email, String my_password,String role) {
        if (my_name.isEmpty()){
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
        if (my_email.isEmpty()){
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
        if (my_password.isEmpty()){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
        if (my_password.length() < 8){
            Toast.makeText(this, "password at least 8 character", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

        authentication(my_name, my_email, my_password, role);

    }

    private void authentication(String my_name, String my_email, String my_password, String is_role) {
        auth.createUserWithEmailAndPassword(my_email,my_password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    AuthConstructor user = new AuthConstructor(my_name,my_email,my_password,is_role);
                    mDatabase.child(auth.getUid()).setValue(user);
                    Intent a = new Intent(getApplicationContext(), Login.class);
                    startActivity(a);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, "Failure:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}