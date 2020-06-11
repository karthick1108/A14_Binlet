package com.gloriousfour.binlet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=2500;
    ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_splash);
        splashImage = (ImageView) findViewById(R.id.splashImage);
        splashImage.startAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));
        goToMainPage();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    void goToMainPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
                if (preferences.contains("isUserLogin")) {
                    Intent welcomeIntent = new Intent(SplashActivity.this, StartGameActivity.class);
                    startActivity(welcomeIntent);
                    finish();
                } else {
                    Intent landingIntent = new Intent(SplashActivity.this, LandingScreenActivity.class);
                    startActivity(landingIntent);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);
    }
}
