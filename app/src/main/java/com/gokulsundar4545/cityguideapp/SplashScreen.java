package com.gokulsundar4545.cityguideapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    ImageView backgroundimage;
    TextView poweredbyline;

    Animation sideanim,bottomanim;

    private static  int SPLASH_TIMER = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        backgroundimage=findViewById(R.id.background_image);
        poweredbyline=findViewById(R.id.powederbyline);

        sideanim= AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomanim=AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        backgroundimage.setAnimation(sideanim);
        poweredbyline.setAnimation(bottomanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(SplashScreen.this,RetailerStartupScreen.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIMER);


    }
}