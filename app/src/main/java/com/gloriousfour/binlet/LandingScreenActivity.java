package com.gloriousfour.binlet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class LandingScreenActivity extends AppCompatActivity {
    Button  btn_guest,btn_login;
    TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_landing_screen);

        /*SpannableString s = new SpannableString("B!NLET");
        s.setSpan(new TypefaceSpan("assets/bold.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        System.out.println("Value is: "+s);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);*/
       // getSupportActionBar().setTitle("B!NLET");
        welcomeText = findViewById(R.id.welcomeText);
        welcomeText.startAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));
        btn_guest = findViewById(R.id.btn_guest);
        btn_guest.startAnimation(AnimationUtils.loadAnimation(this,R.anim.lefttoright));
        btn_login = findViewById(R.id.btn_login);
        btn_login.startAnimation(AnimationUtils.loadAnimation(this,R.anim.lefttoright));
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LandingScreenActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        btn_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomeIntent = new Intent(LandingScreenActivity.this,GuestStartGameActivity.class);
                startActivity(welcomeIntent);
            }
        });
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

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
