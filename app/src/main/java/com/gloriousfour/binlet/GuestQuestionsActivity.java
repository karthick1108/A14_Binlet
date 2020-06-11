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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class GuestQuestionsActivity extends AppCompatActivity {
    TextView guesttv;
    Button guestsubmitbutton, guestquitbutton;
    RadioGroup guestradio_g;
    RadioButton guestrb1,guestrb2,guestrb3,guestrb4;
    String questions[] = {
            "1.What does the three R's of recycling mean?",
            "2.Which of the following items cannot be recycled?",
            "3.What can you make with old plastic bottles?",
            "4.Which gas is produced at landfills?",
            "5.What is e-waste?"
    };

    String answers[] = {
            "Reduce Recycle Reuse",
            "Styrofoam",
            "All of the above",
            "Methane",
            "Electronic waste"
    };

    String opt[] = {
            "Risk Report Reuse","Reading Writing and Arithmetic","Reduce Recycle Reuse","None",
            "Food & organic items","Paper","Batteries/Bulbs","Styrofoam",
            "Plastic, lumber, and furniture","Toys and rulers","Shirts and bags","All of the above",
            "Methane","Petrol","Diesel","Alcohol",
            "Environmental waste","Economic waste","Electronic waste","Educational waste"
    };
    int flag=0;
    public static int marks=0,correct=0,wrong=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_questions);
        getSupportActionBar().setTitle("Quiz");
        final TextView score = (TextView)findViewById(R.id.guesttextView4);
        TextView textView=(TextView)findViewById(R.id.guestDispName);
        Intent intent = getIntent();
        String name= intent.getStringExtra("myname");
        textView.setText("Hello " + name);
        guestsubmitbutton=(Button)findViewById(R.id.guestbutton3);
        guestquitbutton=(Button)findViewById(R.id.guestbuttonquit);
        guesttv=(TextView) findViewById(R.id.guesttvque);
        guestradio_g=(RadioGroup)findViewById(R.id.guestanswersgrp);
        guestrb1=(RadioButton)findViewById(R.id.guestradioButton);
        guestrb2=(RadioButton)findViewById(R.id.guestradioButton2);
        guestrb3=(RadioButton)findViewById(R.id.guestradioButton3);
        guestrb4=(RadioButton)findViewById(R.id.guestradioButton4);
        guesttv.setText(questions[flag]);
        guesttv.startAnimation(AnimationUtils.loadAnimation(this,R.anim.lefttoright));
        guestrb1.setText(opt[0]);
        guestrb1.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
        guestrb2.setText(opt[1]);
        guestrb2.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
        guestrb3.setText(opt[2]);
        guestrb3.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
        guestrb4.setText(opt[3]);
        guestrb4.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));

        guestsubmitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(guestradio_g.getCheckedRadioButtonId()==-1)
                {
                    Toasty.info(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton uans = (RadioButton) findViewById(guestradio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
                if(ansText.equals(answers[flag])) {
                    correct++;
                    final Toast toast = Toasty.success(getApplicationContext(), "Yes, that's Correct", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 500);
                }
                else {
                    wrong++;

                    final Toast toast = Toasty.error(getApplicationContext(), "Oops. Wrong answer", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 500);
                }

                flag++;

                if (score != null)
                    score.setText(""+correct);

                if(flag<questions.length)
                {
                    guesttv.setText(questions[flag]);
                    guesttv.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                    guestrb1.setText(opt[flag*4]);
                    guestrb1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                    guestrb2.setText(opt[flag*4 +1]);
                    guestrb2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                    guestrb3.setText(opt[flag*4 +2]);
                    guestrb3.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                    guestrb4.setText(opt[flag*4 +3]);
                    guestrb4.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                }
                else
                {
                    marks=correct;
                    Intent in = new Intent(getApplicationContext(),GuestResultActivity.class);
                    startActivity(in);
                }
                guestradio_g.clearCheck();
            }
        });

        guestquitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),GuestResultActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(),"See you soon",
                                Toast.LENGTH_SHORT).show();
                        GuestQuestionsActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}
