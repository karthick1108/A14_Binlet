package com.gloriousfour.binlet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import es.dmoral.toasty.Toasty;

public class GuestQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_quiz);
        getSupportActionBar().setTitle("Quiz");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.animate));
        Button startbutton=(Button)findViewById(R.id.startButton);
        startbutton.startAnimation(AnimationUtils.loadAnimation(this,R.anim.bottom_animation));
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.guestquiz);
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
                        Intent gameIntent = new Intent(getApplicationContext(),GuestStartGameActivity.class);
                        startActivity(gameIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.guestquiz:
                        return true;
                }
                return false;
            }
        });
        final EditText nametext=(EditText)findViewById(R.id.editName);
        nametext.startAnimation(AnimationUtils.loadAnimation(this,R.anim.bottom_animation));
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nametext.getText().toString();
                if(!name.matches("")) {
                    if (name.matches("^[a-zA-Z]*$")) {
                        System.out.println("The length is: " + name.length());
                        Intent intent = new Intent(getApplicationContext(), GuestQuestionsActivity.class);
                        intent.putExtra("myname", name);
                        startActivity(intent);

                    } else {
                        Toasty.error(getApplicationContext(), "Please enter only alphabets",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toasty.error(getApplicationContext(), "Please enter your name",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(GuestQuizActivity.this, GuestStartGameActivity.class);
        startActivity(intent);
    }

}

