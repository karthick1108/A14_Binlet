package com.gloriousfour.binlet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StartGameActivity extends AppCompatActivity {
    TextView username;
    Button startGame;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        getSupportActionBar().setTitle("B!NLET");
        username = (TextView) findViewById(R.id.username);
        startGame = (Button) findViewById(R.id.startGame);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        email = sp.getString("email",null);
        username.setText("Welcome "+email);
        username.startAnimation(AnimationUtils.loadAnimation(this,R.anim.lefttoright));
        startGame.startAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.game);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity( new Intent(getApplicationContext(),WelcomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.game:
                        return true;
                    case R.id.quiz:
                        startActivity( new Intent(getApplicationContext(),SelectQuizActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.leaderboard:
                        startActivity( new Intent(getApplicationContext(),LeaderboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.account:
                        startActivity( new Intent(getApplicationContext(),AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(StartGameActivity.this,GameActivity.class);
                startActivity(gameIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
