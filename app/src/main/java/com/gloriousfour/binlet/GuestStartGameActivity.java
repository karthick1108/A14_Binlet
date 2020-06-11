package com.gloriousfour.binlet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GuestStartGameActivity extends AppCompatActivity {
    Button startGame;
    TextView guestname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_start_game);
        getSupportActionBar().setTitle("B!NLET");
        guestname = (TextView) findViewById(R.id.guestname);
        guestname.setText("Welcome Guest");
        guestname.startAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));
        startGame = (Button) findViewById(R.id.startGame);
        startGame.startAnimation(AnimationUtils.loadAnimation(this,R.anim.bottom_animation));
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.guestgame);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.guesthome:
                        startActivity( new Intent(getApplicationContext(),GuestWelcomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.guestgame:
                        return true;
                    case R.id.guestquiz:
                        startActivity( new Intent(getApplicationContext(),GuestQuizActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(GuestStartGameActivity.this,GuestGameActivity.class);
                startActivity(gameIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(GuestStartGameActivity.this, LandingScreenActivity.class);
        startActivity(intent);
    }
}
