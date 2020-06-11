package com.gloriousfour.binlet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GuestShowInfoActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_guest_show_info);
        getSupportActionBar().setTitle("Information");
        showInformation = (ImageView) findViewById(R.id.showInformation);
        Intent intent = getIntent();
        bin = intent.getStringExtra("BIN");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.guesthome);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.guesthome:
                        Intent homeIntent = new Intent(getApplicationContext(),GuestWelcomeActivity.class);
                        startActivity(homeIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.guestgame:
                        Intent gameIntent = new Intent(getApplicationContext(),GuestStartGameActivity.class);
                        startActivity(gameIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.guestquiz:
                        startActivity( new Intent(getApplicationContext(),GuestQuizActivity.class));
                        overridePendingTransition(0,0);
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

    /*@Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(GuestShowInfoActivity.this, GuestWelcomeActivity.class);
        startActivity(intent);
    }*/
}
