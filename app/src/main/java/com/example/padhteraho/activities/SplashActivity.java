package com.example.padhteraho.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.os.Handler;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.padhteraho.MainActivity;
import com.example.padhteraho.R;

public class SplashActivity extends AppCompatActivity {

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);


        // Logo find karo
        logo = findViewById(R.id.logo);


        // Animation load karo
        Animation animation =
                AnimationUtils.loadAnimation(
                        this,
                        R.anim.logo_pop
                );


        // Animation start karo
        logo.startAnimation(animation);

        new Handler().postDelayed(() -> {

            Intent intent = new Intent(
                    SplashActivity.this,
                    MainActivity.class
            );

            startActivity(intent);

            finish();

        }, 2000);


    }
}