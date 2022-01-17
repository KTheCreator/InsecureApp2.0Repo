package com.example.insecureapp20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class successMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_main_menu);
    }

    public void switchToTracker(View view){
        Intent intent = new Intent (this,FitHistoryViewer.class);
        startActivity(intent);
        finish();
    }

    public void switchToDataEntry(View view){
        Intent intent = new Intent (this,dataEntryForUser.class);
        startActivity(intent);
        finish();
    }

    public void logout(View view){
        SharedPreferences userPrefs = getApplicationContext().getSharedPreferences("userPrefs",0);
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.clear().apply();
        Intent intent = new Intent (this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}