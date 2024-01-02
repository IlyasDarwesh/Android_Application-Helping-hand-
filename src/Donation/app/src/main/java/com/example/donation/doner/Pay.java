package com.example.donation.doner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.donation.AuthConstructor;
import com.example.donation.R;
import com.example.donation.constructor.PaymentConstructor;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Pay extends AppCompatActivity {
    Button pay;
    TextView tv_amount;
    EditText my_card_name,my_card_no,my_card_expiry,my_card_ccv;
    String card_name,card_no,card_expiry,card_ccv,card_amount;

    FirebaseAuth auth;
    FirebaseUser userA = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Donation");

    SimpleDateFormat formatter;
    Date date;

    String trustName, trustEmail;

    LinearLayout payment_form;
    LottieAnimationView animation_pay_success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            trustName =(String) b.get("trustName");
            trustEmail =(String) b.get("trustEmail");
        }else {
            trustName = "Default Trust";
            trustEmail = "Default Email";
        }

        auth = FirebaseAuth.getInstance();

        pay = findViewById(R.id.btn_Pay);
        tv_amount = findViewById(R.id.tv_amount);

        my_card_name = findViewById(R.id.et_card_name);
        my_card_no = findViewById(R.id.et_card_number);
        my_card_expiry = findViewById(R.id.et_expiry);
        my_card_ccv = findViewById(R.id.et_ccv);

        animation_pay_success = findViewById(R.id.animation_payment_success);
        payment_form = findViewById(R.id.layout_card_form);

        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        date = new Date();
        Toast.makeText(this, ""+formatter.format(date)+trustEmail+trustName, Toast.LENGTH_SHORT).show();


        ChipGroup chipGroup = findViewById(R.id.chip_group_choice);
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, int checkedId) {
                Chip chip = chipGroup.findViewById(checkedId);
                if (chip != null){
                    tv_amount.setText(chip.getChipText()+".00");
                    pay.setText("Pay"+" "+chip.getChipText()+".00");
                }

            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pay.getText().toString().equals("Goto Home Page")){
                    card_name = my_card_name.getText().toString().trim();
                    card_no = my_card_no.getText().toString().trim();
                    card_expiry = my_card_expiry.getText().toString();
                    card_ccv = my_card_ccv.getText().toString().trim();
                    card_amount = tv_amount.getText().toString();
                    validation(card_name,card_no,card_expiry,card_ccv,card_amount);
                }else {
                    Intent i = new Intent(getApplicationContext(), DonerHome.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private void validation(String card_name, String card_no, String card_expiry, String card_ccv, String card_amount) {
        try {
            PaymentConstructor user = new PaymentConstructor(card_name,userA.getEmail(),card_amount,trustName,trustEmail,formatter.format(date));
            mDatabase.push().setValue(user);
            payment_form.setVisibility(View.GONE);
            animation_pay_success.setVisibility(View.VISIBLE);
            pay.setText("Payment Successfull..");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pay.setText("Goto Home Page");
                }
            }, 3000);
        }catch (Exception e){
            Toast.makeText(this, "Error::"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}