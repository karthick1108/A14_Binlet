package com.gloriousfour.binlet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShowInfoActivity extends AppCompatActivity {
    String bin;
    ImageView showInformation;

    int informationImages[] = {
            R.drawable.cardboardinfo,
            R.drawable.glassinfo,
            R.drawable.organicinfo,
            R.drawable.plasticinfo
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);
        getSupportActionBar().setTitle("Information");
        showInformation = (ImageView) findViewById(R.id.showInformation);
        Intent intent = getIntent();
        bin = intent.getStringExtra("BIN");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        Intent homeIntent = new Intent(getApplicationContext(),WelcomeActivity.class);
                        startActivity(homeIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.game:
                        Intent gameIntent = new Intent(getApplicationContext(),StartGameActivity.class);
                        startActivity(gameIntent);
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
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        if (bin.matches("Yellow"))
        {
            showInformation.setImageResource(informationImages[0]);
        }
        if (bin.matches("Green"))
        {
            showInformation.setImageResource(informationImages[1]);
        }
        if (bin.matches("Black"))
        {
            showInformation.setImageResource(informationImages[2]);
        }
        if (bin.matches("Orange"))
        {
            showInformation.setImageResource(informationImages[3]);
        }

    }


}
