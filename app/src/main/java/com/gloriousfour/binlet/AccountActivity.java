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

public class AccountActivity extends AppCompatActivity {
    TextView tv_account;
    Button b_logout;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().setTitle("Account");
        SharedPreferences sp = getApplicationContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        email = sp.getString("email",null);
        tv_account = (TextView) findViewById(R.id.tv_account);
        b_logout = (Button) findViewById(R.id.b_logout);
        b_logout.startAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));
        tv_account.setText("Account Name: "+email);
        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getSharedPreferences("USER_DATA",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
                Intent loginIntent = new Intent(AccountActivity.this,LandingScreenActivity.class);
                startActivity(loginIntent);
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.account);
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
                        startActivity( new Intent(getApplicationContext(),StartGameActivity.class));
                        overridePendingTransition(0,0);
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
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(AccountActivity.this, StartGameActivity.class);
        startActivity(intent);
    }
}
