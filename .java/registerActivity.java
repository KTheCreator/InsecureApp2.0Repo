package com.example.insecureapp20;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registerActivity extends AppCompatActivity {

    private EditText etName, etEmail,etPassword,etValidatePass;
    private Button btnReg;
    private String URL = "http://10.0.2.2/login/registration.php";
    private String name,email,password,reenterPassword;
    private TextView regStatus;
    private static final String TAG = "regActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeractivity);
        etName = findViewById(R.id.userNameInput);
        etEmail = findViewById(R.id.userEmailInput);
        etPassword = findViewById(R.id.userPassInput);
        etValidatePass = findViewById(R.id.validatePass);
        btnReg = findViewById(R.id.registerUserButton);
        name = email = password = reenterPassword = "";
        regStatus = findViewById(R.id.regStatus);

    }

    public void save(View view){
        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        reenterPassword = etValidatePass.getText().toString().trim();
        if(!password.equals(reenterPassword)){
            Toast.makeText(this,"Passwords do not match! Try again",Toast.LENGTH_SHORT).show();
        }
        else if(!name.equals("")&&!email.equals("")&&!password.equals("")){
            Log.d(TAG,"Starting process");
            StringRequest stringRequest = new StringRequest(Request.Method.POST,URL,new Response.Listener<String>(){
                @Override
                public void onResponse(String response){
                    //Log.d(TAG,"The response is: " + response);
                    if(response.equals("success")){
                        Log.d(TAG,"Success");
                        regStatus.setText("Successful Registration");
                        btnReg.setClickable(false);
                    }
                    else if(response.equals("failure")){
                        Log.d(TAG,"Failed");
                        regStatus.setText("Failed Registration");
                    }

                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"Error");
                }
            }){
                @Override
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String,String> data = new HashMap<>();
                    data.put("name",name);
                    data.put("email",email);
                    data.put("password",password);
                    Log.d(TAG,"The data posted is: " + data);

                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }
    }

    public void login(View view){
        Intent intent = new Intent (this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}