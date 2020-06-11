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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class QuestionsActivity extends AppCompatActivity {
    TextView tv;
    Button submitbutton, quitbutton;
    RadioGroup radio_g;
    RadioButton rb1,rb2,rb3,rb4;
    ImageView imgque;

    boolean imageQues[] = {true,true,true,false,false,false,false,true,false,false};

    String basicQuestions[] = {
            "1.The image below represents what type of waste?",
            "2.The image comes under what type of waste?",
            "3.The image below represents what type of waste?",
            "4.What should you do if you go to get groceries with your parents?",
            "5.What is Recycling?",
            "6.What does Reduce mean?",
            "7.What can you make with old plastic bottles?",
            "8.The image below represents what type of waste?",
            "9.Sarah has soup tins, baked beans tins and tuna tins. Which recycling bin should Sarah put these tins in?",
            "10.Who is responsible for conserving our environment?"};

    int basicQuestionImages[] = {
    R.drawable.ewaste,
    R.drawable.metal,
    R.drawable.food,
    -1,
    -1,
    -1,
    -1,
     R.drawable.plasticfloat,
     -1,
     -1
    };
    
    String intermediateQuestions[] = {
            "1.What does the three R's of recycling mean?",
            "2.Which of the following items cannot be recycled?",
            "3.Which bin does cardboard go in?",
            "4.Which bin would you put grass clippings or leaves?",
            "5.What is e-waste?",
            "6.Why should we reuse products?",
            "7.What's the largest contributor of waste in Australia?",
            "8.If your bicycle tyre gets punctured, what should you do first?",
            "9.Where does the majority of plastic waste end up?",
            "10.Which of the following items do Australians recycle most?"};

    String advancedQuestions[] = {
            "1.How many years does it take for glass to break down naturally?",
            "2.The biggest problem with recycling is that",
            "3.Which gas is produced at landfills?",
            "4._____ of the average household bin is food waste?",
            "5.If you recycle a tonne of paper, how many trees are you saving?",
            "6.How many years does it take a single aluminium can to decompose?",
            "7.Why should we practice 3R?",
            "8.Which of the following is bad for the environment?",
            "9._____ contributes to Global warming?",
            "10.Toxic waste can cause harm to?"
    };

    String basicAnswers[] = {
            "E-waste",
            "Metal",
            "Food",
            "Carry your own bag",
            "Reusing items",
            "Use less",
            "All of the above",
            "Plastic",
            "Metal","All"};

    String intermediateAnswers[] = {
            "Reduce Recycle Reuse",
            "Tissues",
            "Blue",
            "Green",
            "Electronic waste",
            "To generate less waste",
            "Plastic bottles",
            "Try to Fix it",
            "Oceans",
            "Computers and TV Screens"};

    String advancedAnswers[] = {
            "One million",
            "It is costly",
            "Methane",
            "35%",
            "17 trees",
            "80–100 years",
            "To conserve our environment",
            "Littering",
            "Methane",
            "All"
    };

    String basicOpt[] = {
            "Plastic","Metal","E-waste","Food",
            "Hard waste","Food","Paper","Metal",
            "E-waste","Metal","Food","Plastic",
            "Purchase a new bag","Carry your own bag","Throw the bag after use","Forget the bag",
            "Repairing items","Destroy items","Reducing items","Reusing items",
            "Use plenty","Use more","Use less","Use nothing",
            "Plastic, lumber, and furniture","Toys and rulers","Shirts and bags","All of the above",
            "Food","Plastic","Metal","Hard waste",
            "Paper","Glass","Plastic","Metal",
            "School","Parents","Teachers","All"
    };


    String intermediateOpt[] = {
            "Risk Report Reuse","Reading Writing and Arithmetic","Reduce Recycle Reuse","None",
            "Tissues","Paper","Batteries/Bulbs","Yoghurt containers",
            "Yellow","Blue","Green","Red",
            "Blue","Green","Red","Yellow",
            "Environmental waste","Economic waste","Electronic waste","Educational waste",
            "To save money","To play with similar items","To generate less waste","All of the above",
            "Coffee cups","Plastic bags","Nappies","Plastic bottles",
            "Try to Fix it","Get a new tyre","Depends on my mood","Ignore",
            "Burned for energy","Landfills","Recycled","Oceans",
            "Computers and TV Screens","Batteries","Mobile phones","Floppy disk"};

    String advancedOpt[] = {
            "100,000","One Million","Five Million","10 Million",
            "It decreases Carbon dioxide levels","It can contaminate water supplies","It releases lots of methane","It is costly",
            "Methane","Ethane","Diesel","Ethanol",
            "30%","35%","40%","50%",
            "12 trees","17 trees","23 trees","28 trees",
            "20–40 years","60–80 years","80–100 years","100–120 years",
            "To conserve our environment","To produce pollution","To destroy the Earth","For fun",
            "Recycling","Littering","Reusing","Reducing",
            "Alcohol","Methane","Water","Ethane",
            "Plants","Animals","People","All"
    };
    int flag=0;
    public static int marks=0,correct=0,wrong=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        getSupportActionBar().setTitle("Quiz");
        Intent intent = getIntent();
        String value = intent.getStringExtra("VALUE");
        submitbutton = (Button) findViewById(R.id.button3);
        quitbutton = (Button) findViewById(R.id.buttonquit);
        tv = (TextView) findViewById(R.id.tvque);
        radio_g = (RadioGroup) findViewById(R.id.answersgrp);
        rb1 = (RadioButton) findViewById(R.id.radioButton);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
        rb3 = (RadioButton) findViewById(R.id.radioButton3);
        rb4 = (RadioButton) findViewById(R.id.radioButton4);
        imgque= (ImageView) findViewById(R.id.imgque);
        imgque.setVisibility(View.INVISIBLE);

        if (value.matches("basic")) {
            tv.setText(basicQuestions[flag]);
            tv.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            if (imageQues[0]) {
                imgque.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
                imgque.setVisibility(View.VISIBLE);
                imgque.setImageResource(basicQuestionImages[0]);
            }
            rb1.setText(basicOpt[0]);
            rb1.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            rb2.setText(basicOpt[1]);
            rb2.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            rb3.setText(basicOpt[2]);
            rb3.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            rb4.setText(basicOpt[3]);
            rb4.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            submitbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radio_g.getCheckedRadioButtonId() == -1) {
                        Toasty.info(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                    String ansText = uans.getText().toString();
                    if (ansText.equals(basicAnswers[flag])) {
                        correct++;
                       // Toasty.success(getApplicationContext(), "Yes, that's Correct", Toast.LENGTH_SHORT).show();
                        final Toast toast = Toasty.success(getApplicationContext(), "Yes, that's Correct", Toast.LENGTH_SHORT);
                        toast.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 500);
                    } else {
                        wrong++;
                       // Toasty.error(getApplicationContext(), "Oops. Wrong answer", Toast.LENGTH_SHORT).show();
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

                    if (flag < basicQuestions.length) {
                        tv.setText(basicQuestions[flag]);
                        tv.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                        rb1.setText(basicOpt[flag * 4]);
                        rb1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                        rb2.setText(basicOpt[flag * 4 + 1]);
                        rb2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                        rb3.setText(basicOpt[flag * 4 + 2]);
                        rb3.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                        rb4.setText(basicOpt[flag * 4 + 3]);
                        rb4.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                        if (imageQues[flag]) {
                            imgque.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                            imgque.setVisibility(View.VISIBLE);
                            imgque.setImageResource(basicQuestionImages[flag]);
                        }
                        else {
                            imgque.setVisibility(View.INVISIBLE);
                            imgque.setImageDrawable(null);
                        }
                    } else {
                        marks = correct;
                        Intent in = new Intent(getApplicationContext(), ResultActivity.class);
                        startActivity(in);
                    }
                    radio_g.clearCheck();
                }
            });
        }

        if (value.matches("intermediate")) {

            tv.setText(intermediateQuestions[flag]);
            tv.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            rb1.setText(intermediateOpt[0]);
            rb1.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            rb2.setText(intermediateOpt[1]);
            rb2.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            rb3.setText(intermediateOpt[2]);
            rb3.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            rb4.setText(intermediateOpt[3]);
            rb4.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            submitbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radio_g.getCheckedRadioButtonId() == -1) {
                        Toasty.info(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                    String ansText = uans.getText().toString();
                    if (ansText.equals(intermediateAnswers[flag])) {
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
                    } else {
                        wrong++;
                        //Toasty.error(getApplicationContext(), "Oops. Wrong answer", Toast.LENGTH_SHORT).show();
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

                    if (flag < intermediateQuestions.length) {
                        tv.setText(intermediateQuestions[flag]);
                        tv.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                        rb1.setText(intermediateOpt[flag * 4]);
                        rb1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                        rb2.setText(intermediateOpt[flag * 4 + 1]);
                        rb2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                        rb3.setText(intermediateOpt[flag * 4 + 2]);
                        rb3.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                        rb4.setText(intermediateOpt[flag * 4 + 3]);
                        rb4.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                    } else {
                        marks = correct;
                        Intent in = new Intent(getApplicationContext(), ResultActivity.class);
                        startActivity(in);
                    }
                    radio_g.clearCheck();
                }
            });
        }


        if (value.matches("advanced")) {

            tv.setText(advancedQuestions[flag]);
            tv.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            rb1.setText(advancedOpt[0]);
            rb1.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            rb2.setText(advancedOpt[1]);
            rb2.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            rb3.setText(advancedOpt[2]);
            rb3.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            rb4.setText(advancedOpt[3]);
            rb4.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
            submitbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radio_g.getCheckedRadioButtonId() == -1) {
                        Toasty.info(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                    String ansText = uans.getText().toString();
                    if (ansText.equals(advancedAnswers[flag])) {
                        correct++;
                        //Toasty.success(getApplicationContext(), "Yes, that's Correct", Toast.LENGTH_SHORT).show();
                        final Toast toast = Toasty.success(getApplicationContext(), "Yes, that's Correct", Toast.LENGTH_SHORT);
                        toast.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 500);
                    } else {
                        wrong++;
                        //Toasty.error(getApplicationContext(), "Oops. Wrong answer", Toast.LENGTH_SHORT).show();
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

                    if (flag < advancedQuestions.length) {
                        tv.setText(advancedQuestions[flag]);
                        tv.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                        rb1.setText(advancedOpt[flag * 4]);
                        rb1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                        rb2.setText(advancedOpt[flag * 4 + 1]);
                        rb2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                        rb3.setText(advancedOpt[flag * 4 + 2]);
                        rb3.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                        rb4.setText(advancedOpt[flag * 4 + 3]);
                        rb4.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
                    } else {
                        marks = correct;
                        Intent in = new Intent(getApplicationContext(), ResultActivity.class);
                        startActivity(in);
                    }
                    radio_g.clearCheck();
                }
            });
        }

        quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ResultActivity.class);
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
                        QuestionsActivity.this.finish();
                        Intent intent = new Intent(QuestionsActivity.this, SelectQuizActivity.class);
                        startActivity(intent);
                    }
                }).create().show();
    }
    }
