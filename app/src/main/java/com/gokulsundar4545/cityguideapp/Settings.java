package com.gokulsundar4545.cityguideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void changepassword(View view){
        startActivity(new Intent(Settings.this,ForgetPassword.class));
    }

    public void callprofileback(View view){
        startActivity(new Intent(Settings.this,Profile.class));
    }
}