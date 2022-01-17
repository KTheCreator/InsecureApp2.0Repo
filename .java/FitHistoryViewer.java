package com.example.insecureapp20;

import android.content.SharedPreferences;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FitHistoryViewer extends AppCompatActivity {

    private String URL = "http://10.0.2.2/login/fetchData.php";
    private RecyclerView recyclerView;
    private static final String TAG = "fitDisplayActivity";
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> weights = new ArrayList<>();
    private ArrayList<String> bodyFats = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_history_viewer);
        recyclerView = findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"The user's name is: "+response);
                fitHistoryArray(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();
                Log.d(TAG,"Error");
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> data = new HashMap<>();
                SharedPreferences userPrefs = getApplicationContext().getSharedPreferences("userPrefs",0);
                data.put("userEmail",userPrefs.getString("currUserEmail",null));
                return data;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
//Code inspired: https://abhiandroid.com/programming/json
    private void fitHistoryArray(String response){
        try{
            JSONObject obj = new JSONObject(response);
            JSONArray fitHistoryJArray = obj.getJSONArray("fitHistory");
            for(int i = 0; i < fitHistoryJArray.length();i++){
                JSONObject fitDetail = fitHistoryJArray.getJSONObject(i);
                dates.add("Date: " + fitDetail.getString("Date:"));
                weights.add("Weight: "+fitDetail.getString("Weight:")+" KG");
                bodyFats.add("Body Fat: "+fitDetail.getString("BodyFat")+"%");
            }
            Log.d(TAG,"Number of entries: "+fitHistoryJArray.length());
            for(int i = 0; i <fitHistoryJArray.length();i++){
                Log.d(TAG,"Entry: "+i+" : "+fitHistoryJArray.getJSONObject(i));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        CustomAdapter customAdapter = new CustomAdapter(FitHistoryViewer.this,dates,weights,bodyFats);
        recyclerView.setAdapter(customAdapter);
    }
}