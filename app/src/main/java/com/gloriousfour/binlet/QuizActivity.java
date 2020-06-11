package com.gloriousfour.binlet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QuizActivity extends AppCompatActivity {
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Button startbutton=(Button)findViewById(R.id.startButton);
        final EditText nametext=(EditText)findViewById(R.id.editName);
        Intent intent = getIntent();
        value = intent.getStringExtra("VALUE");
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

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nametext.getText().toString();
                if(!name.matches(""))
                {
                    Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                    intent.putExtra("myname", name);
                    intent.putExtra("VALUE",value);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(getApplicationContext(),"Please enter your name",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
