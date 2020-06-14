package com.example.userprofile.Splash_Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.userprofile.R;
import com.example.userprofile.Intero_Screen.Welcome_Intro;

public class SplashScreen1 extends AppCompatActivity {
private static final int splash_time_out=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splash_intent=new Intent(SplashScreen1.this, Welcome_Intro.class);
                startActivity(splash_intent);
                finish();
            }
        },splash_time_out);

    }
}