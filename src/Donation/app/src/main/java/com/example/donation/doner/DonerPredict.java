package com.example.donation.doner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.donation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class DonerPredict extends AppCompatActivity {
    private static final String TAG = DonerPredict.class.getName();
    private Button btnRequest;

    EditText my_year;
    Spinner my_district;
    TextView tv_pridiction,tv_pridiction2;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doner_predict);

        btnRequest = (Button) findViewById(R.id.buttonRequest);
        my_year = findViewById(R.id.get_year);
        my_district = findViewById(R.id.get_district);
        tv_pridiction = findViewById(R.id.tv_prediction);
        tv_pridiction2 = findViewById(R.id.tv_prediction2);

        btnRequest.setOnClickListener(
                new View.OnClickListener() {
              @Override
              public void onClick(View v){
                  sendAndRequestResponse();
              }
          }
        );
    }

    private void sendAndRequestResponse() {
        String year = my_year.getText().toString().trim();
        String district = my_district.getSelectedItem().toString().trim();
        url = "https://134c-115-186-190-57.ap.ngrok.io/?year="+year+"&&dict="+district+"";
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String str = response;
                    JSONObject jsonObject = null;
                    jsonObject = new JSONObject(str);
                    float literacy = jsonObject.getInt("literacy");
                    float employment = jsonObject.getInt("employment");
                    setValues(literacy,employment);
                    Toast.makeText(getApplicationContext(),"literacy" + literacy +" "+"employment"+employment, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    private void setValues(float literacy, float employment) {
        if (literacy<=30){
            tv_pridiction.setText("Litracy:"+" "+"Severe Poverty");
        }
        if (literacy>30 && literacy<=35){
            tv_pridiction.setText("Litracy:"+" "+"Moderate Poverty");
        }
        if (literacy>35 && literacy>40){
            tv_pridiction.setText("Litracy:"+" "+"No Poverty");
        }
        if (employment<=40){
            tv_pridiction2.setText("Employment:"+" "+"Severe Poverty");
        }
        if (employment>40 && employment<50){
            tv_pridiction2.setText("Employment:"+" "+"Moderate Poverty");
        }
        if (employment>50 && employment>60){
            tv_pridiction2.setText("Employment:"+" "+"No Poverty");
        }
    }
}