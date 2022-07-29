package com.gokulsundar4545.cityguideapp;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RetailerStartupScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_startup_screen);


        ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo==null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.internetalert);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations=
                    android.R.style.Animation_Dialog;

            Button Retry=dialog.findViewById(R.id.retry123);
            Retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recreate();
                }
            });
            dialog.show();

        }


    }
    public void callLoginScreen(View view){


        Intent intent=new Intent(getApplicationContext(),login.class);
        Pair[] pairs=new Pair[1];
        pairs[0]=new Pair<View, String>(findViewById(R.id.login_btn),"transition_login");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(RetailerStartupScreen.this,pairs);
        startActivity(intent,options.toBundle());
    }

    public void callSignUpScreen (View view){
        Intent intent=new Intent(getApplicationContext(), SignUp.class);
        Pair[] pairs=new Pair[1];
        pairs[0]=new Pair<View, String>(findViewById(R.id.signup_btn),"transition_login");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(RetailerStartupScreen.this,pairs);
        startActivity(intent,options.toBundle());
    }
}