package com.example.insecureapp20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private String email,password;
    private String URL="http://10.0.2.2/login/login.php";

    private static final String TAG = "loginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        email = password = "";
        etEmail = findViewById(R.id.usernameInput);
        etPassword = findViewById(R.id.passwordInput);


    }
    public void loginUser(View view) throws GeneralSecurityException, IOException {



        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        //SharedPreferences userPrefs = getApplication().getSharedPreferences("userPrefs",0);
        String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        SharedPreferences userPrefs= EncryptedSharedPreferences.create(
                "userPrefs_file",
                masterKeyAlias,
                getApplicationContext(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
        SharedPreferences.Editor editor = userPrefs.edit();
        if(!email.equals("")&&!password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST,URL,new Response.Listener<String>(){
                @Override
                public void onResponse(String response){
                    //Log.d(TAG,"Response is: " + response);

                    if(response.trim().equals("success")){
                        Log.d(TAG,"Switching to main menu");
                        editor.putString("currUserEmail",email);
                        editor.apply();
                        Intent intent = new Intent( MainActivity.this, successMainMenu.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(response.trim().equals("failure")){
                        Toast.makeText(MainActivity.this,"Invalid Login Id/Password",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Toast.makeText(MainActivity.this,error.toString().trim(),Toast.LENGTH_SHORT).show();
                    Log.d(TAG,error.toString());
                }
            }){
                @Override
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String,String> data = new HashMap<>();
                    data.put("email",email);
                    data.put("password",password);

                    return data;
                }
            };



            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }else{ Toast.makeText(this,"Fields cannot be empty",Toast.LENGTH_SHORT).show();}
    }

    public void register(View view){
        Intent intent = new Intent (this,registerActivity.class);
        startActivity(intent);
        finish();
    }


}