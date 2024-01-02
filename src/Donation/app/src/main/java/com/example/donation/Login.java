package com.example.donation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donation.admin.AdminHome;
import com.example.donation.deserver.DeserverHome;
import com.example.donation.doner.DonerHome;
import com.example.donation.trust.TrustHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    TextView tv_register;
    EditText get_email, get_password;
    Button btn_login;
    String my_email, my_password;
    ProgressDialog dialog;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        get_email = findViewById(R.id.et_login_email);
        get_password = findViewById(R.id.et_login_password);
        btn_login = findViewById(R.id.btn_login_login);
        tv_register = findViewById(R.id.tv_login_register);

        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("loading...");
        dialog.setCancelable(false);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_email = get_email.getText().toString().trim();
                my_password = get_password.getText().toString().trim();
                dialog.show();
                if (my_email.equals("admin@abc.com") && my_password.equals("12345")){
                    Intent a = new Intent(getApplicationContext(), AdminHome.class);
                    startActivity(a);
                    finish();
                }else {
                    validation(my_email, my_password);
                }
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), Register.class);
                startActivity(a);
            }
        });
    }

    private void validation(String my_email, String my_password) {
        if (my_email.isEmpty()){
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }if (my_password.isEmpty()){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
        if (my_password.length() < 8){
            Toast.makeText(this, "password at least 8 character", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }else {
            authentication(my_email, my_password);
        }
    }
    private void authentication(String my_emails, String my_passwords) {
        auth.signInWithEmailAndPassword(my_emails, my_passwords).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    try {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String get_user_id = user.getUid();
                        check_status(get_user_id);
                        Toast.makeText(Login.this, "Success:.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }catch (Exception e){
                        dialog.dismiss();
                        Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void check_status(String get_user_id) {
        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        mFirebaseDatabaseReference.child(get_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AuthConstructor user = snapshot.getValue(AuthConstructor.class);
                String get_user_status = user.getRole();
                if (get_user_status.equals("Doner")){
                    Intent a = new Intent(getApplicationContext(), DonerHome.class);
                    startActivity(a);
                    finish();
                    Toast.makeText(Login.this, "welcome:"+user.getEmail(), Toast.LENGTH_SHORT).show();
                }
                if (get_user_status.equals("Deserver")){
                    Intent a = new Intent(getApplicationContext(), DeserverHome.class);
                    startActivity(a);
                    finish();
                    Toast.makeText(Login.this, "welcome:"+user.getEmail(), Toast.LENGTH_SHORT).show();
                }
                if (get_user_status.equals("Trust")){
                    Intent a = new Intent(getApplicationContext(), TrustHome.class);
                    startActivity(a);
                    finish();
                    Toast.makeText(Login.this, "welcome:"+user.getEmail(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "oncancelled:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}