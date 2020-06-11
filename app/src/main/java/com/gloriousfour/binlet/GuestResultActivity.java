package com.gloriousfour.binlet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class GuestResultActivity extends AppCompatActivity {
    TextView tv, tv2, tv3;
    //Button btnguestQuit;
    TextView scoreCard;
    AlertDialog.Builder builder;
    Button btnguestQuit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_result);
        getSupportActionBar().setTitle("Result");
        tv = (TextView)findViewById(R.id.tvguestres);
        tv2 = (TextView)findViewById(R.id.tvguestres2);
        tv3 = (TextView)findViewById(R.id.tvguestres3);
        btnguestQuit = (Button) findViewById(R.id.btnguestQuit);
        btnguestQuit.setVisibility(View.INVISIBLE);
        scoreCard = (TextView) findViewById(R.id.scoreCard);
        scoreCard.startAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));
        StringBuffer sb = new StringBuffer();
        sb.append("Correct answers: " + GuestQuestionsActivity.correct + "\n");
        StringBuffer sb2 = new StringBuffer();
        sb2.append("Wrong Answers: " + GuestQuestionsActivity.wrong + "\n");
        StringBuffer sb3 = new StringBuffer();
        sb3.append("Final Score: " + GuestQuestionsActivity.correct + "\n");
        tv.setText(sb);
        tv.startAnimation(AnimationUtils.loadAnimation(this,R.anim.lefttoright));
        tv2.setText(sb2);
        tv2.startAnimation(AnimationUtils.loadAnimation(this,R.anim.lefttoright));
        tv3.setText(sb3);
        tv3.startAnimation(AnimationUtils.loadAnimation(this,R.anim.lefttoright));
        GuestQuestionsActivity.correct=0;
        GuestQuestionsActivity.wrong=0;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callAlertBox();
            }
        }, 2900);


    }

    private void callAlertBox() {

        builder = new AlertDialog.Builder(this);
        builder.setMessage("Like the Quiz? Login to experience more questions ")
                .setCancelable(false)
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent loginIntent = new Intent(GuestResultActivity.this,LoginActivity.class);
                        startActivity(loginIntent);
                    }
                })
                .setNeutralButton("Restart Quiz", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent in = new Intent(getApplicationContext(),GuestQuizActivity.class);
                        startActivity(in);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                btnguestQuit.setVisibility(View.VISIBLE);
                                btnguestQuit.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                                btnguestQuit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(GuestResultActivity.this, GuestQuizActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
        AlertDialog alert = builder.create();
        alert.setTitle("Thank you for playing");
        alert.show();
    }

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(GuestResultActivity.this, GuestQuizActivity.class);
        startActivity(intent);
    }
}
