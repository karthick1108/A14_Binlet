package com.gloriousfour.binlet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    TextView tv, tv2, tv3;
    Button btnQuit;
    TextView scoreCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setTitle("Result");
        tv = (TextView)findViewById(R.id.tvres);
        tv2 = (TextView)findViewById(R.id.tvres2);
        tv3 = (TextView)findViewById(R.id.tvres3);
        btnQuit = (Button) findViewById(R.id.btnQuit);
        scoreCard = (TextView) findViewById(R.id.scoreCard);
        scoreCard.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
        StringBuffer sb = new StringBuffer();
        sb.append("Correct answers: " + QuestionsActivity.correct + "\n");
        StringBuffer sb2 = new StringBuffer();
        sb2.append("Wrong Answers: " + QuestionsActivity.wrong + "\n");
        StringBuffer sb3 = new StringBuffer();
        sb3.append("Final Score: " + QuestionsActivity.correct + "\n");
        tv.setText(sb);
        tv.startAnimation(AnimationUtils.loadAnimation(this,R.anim.lefttoright));
        tv2.setText(sb2);
        tv2.startAnimation(AnimationUtils.loadAnimation(this,R.anim.lefttoright));
        tv3.setText(sb3);
        tv3.startAnimation(AnimationUtils.loadAnimation(this,R.anim.lefttoright));
        QuestionsActivity.correct=0;
        QuestionsActivity.wrong=0;

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),SelectQuizActivity.class);
                startActivity(in);
            }
        });
    }

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(ResultActivity.this, SelectQuizActivity.class);
        startActivity(intent);
    }
}