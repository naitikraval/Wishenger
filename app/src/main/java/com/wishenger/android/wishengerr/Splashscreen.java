package com.wishenger.android.wishengerr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.wishenger.android.wishengerr.RegisterAndLogin.Login;

import maes.tech.intentanim.CustomIntent;

public class Splashscreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(Splashscreen.this, Login.class);
                startActivity(intent);
                CustomIntent.customType(Splashscreen.this,"left-to-right");
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
