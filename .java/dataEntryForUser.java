package com.example.insecureapp20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class dataEntryForUser extends AppCompatActivity {

    EditText etDateEntry, etWeightEntry, etBodyFat;
    String date, weight, BodyFat;
    Button submitButton;
    private String URL = "http://10.0.2.2/login/json.php";
    private static final String TAG = "dataEntryActivity";
    private String message = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry_for_user);
        etDateEntry = findViewById(R.id.dateEntry);
        etWeightEntry = findViewById(R.id.weightEntry);
        etBodyFat = findViewById(R.id.bodyFatEntry);
        date = weight= BodyFat = "";
    }
    public void JSONCreator(View view) throws IOException {
        date = etDateEntry.getText().toString().trim();
        weight = etWeightEntry.getText().toString().trim();
        BodyFat = etBodyFat.getText().toString().trim();

        try {
            JSONObject obj = new JSONObject();
            JSONArray jArray = new JSONArray();
            obj.put("Date:", date);
            obj.put("Weight:",weight);
            obj.put("BodyFat",BodyFat);
            jArray.put(obj);
            message = jArray.toString(2);
            Log.d(TAG,"Message: "+message);
        }catch(JSONException e){
            e.printStackTrace();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"The response is: "+response);
                if(response.equals("success")){
                    Log.d(TAG,"Data Inserted");
                }
                else{
                    Log.d(TAG,"Data was not inserted");
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.d(TAG,"Error");
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Log.d(TAG,"Message: "+message);
                Map<String,String> data = new HashMap<>();
                data.put("fitHistory",message);
                Log.d(TAG,"The data posted is: " + data);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }
}