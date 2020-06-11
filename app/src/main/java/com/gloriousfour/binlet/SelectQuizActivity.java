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

public class SelectQuizActivity extends AppCompatActivity {
    Button basicBtn,intermediateBtn,advancedBtn;
    String value,email;
    TextView selectLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_quiz);
        getSupportActionBar().setTitle("Quiz");
        basicBtn = (Button) findViewById(R.id.basicBtn);
        intermediateBtn = (Button) findViewById(R.id.intermediateBtn);
        advancedBtn = (Button) findViewById(R.id.advancedBtn);
        selectLevel = (TextView) findViewById(R.id.selectLevel);
        selectLevel.startAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));
        basicBtn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.lefttoright));
        intermediateBtn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.bounce));
        advancedBtn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
        SharedPreferences sp = getApplicationContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        email = sp.getString("email",null);
        selectLevel.setText("Select the Category");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.quiz);
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
        basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent basicIntent = new Intent(SelectQuizActivity.this,QuestionsActivity.class);
                value ="basic";
                basicIntent.putExtra("VALUE",value);
                startActivity(basicIntent);
            }
        });
        intermediateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intermediateIntent = new Intent(SelectQuizActivity.this,QuestionsActivity.class);
                value ="intermediate";
                intermediateIntent.putExtra("VALUE",value);
                startActivity(intermediateIntent);
            }
        });
        advancedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent advancedIntent = new Intent(SelectQuizActivity.this,QuestionsActivity.class);
                value ="advanced";
                advancedIntent.putExtra("VALUE",value);
                startActivity(advancedIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(SelectQuizActivity.this, StartGameActivity.class);
        startActivity(intent);
    }
}
