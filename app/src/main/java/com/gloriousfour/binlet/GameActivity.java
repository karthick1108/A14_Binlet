package com.gloriousfour.binlet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class GameActivity extends AppCompatActivity {
    float dX, dY;
    List<GarbageItem> garbageItemList = new ArrayList<GarbageItem>();
    ImageView currentGarbageImage;
    ImageView glassBin,plasticBin,paperBin,organicBin;
    ImageView correctImage1,correctImage2,correctImage3,correctImage4;
    ImageView wrongImage1,wrongImage2,wrongImage3,wrongImage4;
    GarbageItem currentGarbageItem;
    AlertDialog.Builder builder;
    private float originalX,originalY;
    TextView scoreTextView,timerTextView,itemsLeft;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliSeconds = 90000;
    private boolean timerRunning;
    int currentScore,highScore = 0;
    String userEmail="";
    int items = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Game");
        SharedPreferences sp = getApplicationContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        userEmail = sp.getString("email",null);
        GetScoreAsyncTask getScoreAsyncTask = new GetScoreAsyncTask();
        getScoreAsyncTask.execute();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.game);
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
                        startActivity( new Intent(getApplicationContext(),AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        currentGarbageImage = findViewById(R.id.garbageImage);
        glassBin = findViewById(R.id.glassBin);
        plasticBin = findViewById(R.id.plasticBin);
        paperBin = findViewById(R.id.paperBin);
        organicBin = findViewById(R.id.organicBin);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timer);
        itemsLeft = findViewById(R.id.itemsLeft);
        correctImage1 = (ImageView) findViewById(R.id.correctImage1);
        wrongImage1 = (ImageView) findViewById(R.id.wrongImage1);

        correctImage2 = (ImageView) findViewById(R.id.correctImage2);
        wrongImage2 = (ImageView) findViewById(R.id.wrongImage2);

        correctImage3 = (ImageView) findViewById(R.id.correctImage3);
        wrongImage3 = (ImageView) findViewById(R.id.wrongImage3);

        correctImage4 = (ImageView) findViewById(R.id.correctImage4);
        wrongImage4 = (ImageView) findViewById(R.id.wrongImage4);
        correctImage1.setVisibility(View.INVISIBLE);
        wrongImage1.setVisibility(View.INVISIBLE);
        correctImage2.setVisibility(View.INVISIBLE);
        wrongImage2.setVisibility(View.INVISIBLE);
        correctImage3.setVisibility(View.INVISIBLE);
        wrongImage3.setVisibility(View.INVISIBLE);
        correctImage4.setVisibility(View.INVISIBLE);
        wrongImage4.setVisibility(View.INVISIBLE);
        startStop();
        updateTimer();
        populateGarbageItems();
        configureDragAndDrop();
        items = garbageItemList.size()+1;
        itemsLeft.setText("Items: "+items);
    }

    private void startStop() {
        if(timerRunning) {
            stopTimer();
        }
        else {
            startTimer();

        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliSeconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliSeconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timeLeftInMilliSeconds = 0;
                updateTimer();
                callAlertBox();
            }
        }.start();
        timerRunning = true;
    }

    private void callAlertBox() {
        builder = new AlertDialog.Builder(this);
        if(currentScore > highScore)
        {
            countDownTimer.cancel();
            builder.setMessage("Congratulations, your new best is : "+ " "+currentScore)
                    .setCancelable(false)
                    .setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            currentScore = 0;
                            countDownTimer.start();
                            scoreTextView.setText("Score: " + currentScore);
                            items+=32;
                            itemsLeft.setText("Items: "+items);
                            populateGarbageItems();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            countDownTimer.cancel();
                            Toasty.info(getApplicationContext(),"You have decided not to play this game",
                                    Toast.LENGTH_SHORT).show();
                            scoreTextView.setText("Score: " + currentScore);
                            currentGarbageImage.setImageDrawable(null);
                        }
                    });
            UpdateScoreAsyncTask updateScore = new UpdateScoreAsyncTask();
            updateScore.execute(userEmail,Integer.toString(currentScore));
        }

        else
        {
            countDownTimer.cancel();
            builder.setMessage("Your final score is: "+ " "+currentScore+"\n"+"Your Best Score is: "+" "+highScore)
                    .setCancelable(false)
                    .setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            currentScore = 0;
                            countDownTimer.start();
                            timerTextView.setTextColor(Color.parseColor("#737373"));
                            scoreTextView.setText("Score: " + currentScore);
                            items+=32;
                            itemsLeft.setText("Items: "+items);
                            populateGarbageItems();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            countDownTimer.cancel();
                            Toasty.info(getApplicationContext(),"You have decided not to play this game",
                                    Toast.LENGTH_SHORT).show();
                            scoreTextView.setText("Score: " + currentScore);
                            currentGarbageImage.setImageDrawable(null);
                        }
                    });

        }
        AlertDialog alert = builder.create();
        alert.setTitle("Thank you for playing");
        alert.show();
    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning=false;
    }

    private void updateTimer() {
        int minutes = (int) timeLeftInMilliSeconds / 60000;
        int seconds = (int) timeLeftInMilliSeconds % 60000 / 1000;
        String timeLeftText;
        timeLeftText = " " + minutes;
        timeLeftText += ":";
        if(seconds < 10) {
            timeLeftText += "0";
            if(timeLeftInMilliSeconds<10000)
            {
                timerTextView.setTextColor(Color.RED);
                timeLeftText += seconds;
                timerTextView.setText(timeLeftText);
            }
        }
        else {
            timeLeftText += seconds;
            timerTextView.setText(timeLeftText);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void configureDragAndDrop() {
        this.currentGarbageImage.setOnTouchListener(new MyTouchListener());
    }

    private void populateGarbageItems() {
        garbageItemList.add(new GarbageItem(R.drawable.apple,GarbageType.ORGANIC));
        garbageItemList.add(new GarbageItem(R.drawable.banana,GarbageType.ORGANIC));
        garbageItemList.add(new GarbageItem(R.drawable.books,GarbageType.PAPER));
        garbageItemList.add(new GarbageItem(R.drawable.bottle,GarbageType.GLASS));
        garbageItemList.add(new GarbageItem(R.drawable.cardboard,GarbageType.PAPER));
        garbageItemList.add(new GarbageItem(R.drawable.carrot,GarbageType.ORGANIC));
        garbageItemList.add(new GarbageItem(R.drawable.cocktail,GarbageType.PLASTICS));
        garbageItemList.add(new GarbageItem(R.drawable.eggshell,GarbageType.ORGANIC));
        garbageItemList.add(new GarbageItem(R.drawable.envelope,GarbageType.PAPER));
        garbageItemList.add(new GarbageItem(R.drawable.flyer,GarbageType.PAPER));
        garbageItemList.add(new GarbageItem(R.drawable.glassbottle,GarbageType.GLASS));
        garbageItemList.add(new GarbageItem(R.drawable.melon,GarbageType.ORGANIC));
        garbageItemList.add(new GarbageItem(R.drawable.newspaper,GarbageType.PAPER));
        garbageItemList.add(new GarbageItem(R.drawable.papertowel,GarbageType.PAPER));
        garbageItemList.add(new GarbageItem(R.drawable.plasticbottle,GarbageType.PLASTICS));
        garbageItemList.add(new GarbageItem(R.drawable.plasticcup,GarbageType.PLASTICS));
        garbageItemList.add(new GarbageItem(R.drawable.teabag,GarbageType.ORGANIC));
        garbageItemList.add(new GarbageItem(R.drawable.toiletroll,GarbageType.PAPER));
        garbageItemList.add(new GarbageItem(R.drawable.toothpaste,GarbageType.PLASTICS));
        garbageItemList.add(new GarbageItem(R.drawable.wineglasss,GarbageType.GLASS));
        garbageItemList.add(new GarbageItem(R.drawable.bread,GarbageType.ORGANIC));
        garbageItemList.add(new GarbageItem(R.drawable.seafood,GarbageType.ORGANIC));
        garbageItemList.add(new GarbageItem(R.drawable.chicken,GarbageType.ORGANIC));
        garbageItemList.add(new GarbageItem(R.drawable.plant,GarbageType.ORGANIC));
        garbageItemList.add(new GarbageItem(R.drawable.lego,GarbageType.PLASTICS));
        garbageItemList.add(new GarbageItem(R.drawable.cutlery,GarbageType.PLASTICS));
        garbageItemList.add(new GarbageItem(R.drawable.beerbottle,GarbageType.GLASS));
        garbageItemList.add(new GarbageItem(R.drawable.brinjal,GarbageType.ORGANIC));
        garbageItemList.add(new GarbageItem(R.drawable.broccoli,GarbageType.ORGANIC));
        garbageItemList.add(new GarbageItem(R.drawable.mango,GarbageType.ORGANIC));
        garbageItemList.add(new GarbageItem(R.drawable.capsicum,GarbageType.ORGANIC));
        garbageItemList.add(new GarbageItem(R.drawable.plasticbag,GarbageType.PLASTICS));
        setCurrentGarbageItem();
    }

    private void setCurrentGarbageItem() {
        if(garbageItemList.size()>0) {
            Collections.shuffle(garbageItemList);
            currentGarbageItem = garbageItemList.get(0);
            currentGarbageImage.setImageResource(currentGarbageItem.drawable);
            garbageItemList.remove(0);
        }
        else
        {
            currentGarbageImage.setImageDrawable(null);
            Toasty.info(getApplicationContext(),"Sorry. No more Items to show",
                    Toast.LENGTH_SHORT).show();
            callAlertBox();
        }
    }

    void updateCurrentScore() {
        scoreTextView.setText("Score : " + currentScore);
        setCurrentGarbageItem();
    }

    private final class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - event.getRawX();
                    dY = view.getY() - event.getRawY();
                    originalX = currentGarbageImage.getX();
                    originalY = currentGarbageImage.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    if (isViewOverlapping(view,glassBin,"glass")) {
                        System.out.println("GLASS BIN");
                        if (currentGarbageItem.garbageType == GarbageType.GLASS) {
                            increaseScore();
                            correctImage1.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable(){
                                public void run() {
                                    correctImage1.setVisibility(View.INVISIBLE);
                                }
                            }, 600);
                        }
                        else {
                            wrongImage1.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable(){
                                public void run() {
                                    wrongImage1.setVisibility(View.INVISIBLE);
                                }
                            }, 600);
                        }
                        updateCurrentScore();
                        decreaseItems();
                        animateToOrigin(view);
                    }
                    else if (isViewOverlapping(view,plasticBin,"plastic")) {
                        System.out.println("PLASTIC BIN");
                        if (currentGarbageItem.garbageType == GarbageType.PLASTICS) {
                            increaseScore();
                            correctImage2.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable(){
                                public void run() {
                                    correctImage2.setVisibility(View.INVISIBLE);
                                }
                            }, 600);
                        }

                        else {
                            wrongImage2.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable(){
                                public void run() {
                                    wrongImage2.setVisibility(View.INVISIBLE);
                                }
                            }, 600);
                        }
                        updateCurrentScore();
                        decreaseItems();
                        animateToOrigin(view);
                    }
                    else if (isViewOverlapping(view,paperBin,"paper")) {
                        System.out.println("PAPER BIN");
                        if (currentGarbageItem.garbageType == GarbageType.PAPER) {
                            increaseScore();
                            correctImage3.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable(){
                                public void run() {
                                    correctImage3.setVisibility(View.INVISIBLE);
                                }
                            }, 600);
                        }
                        else {
                            wrongImage3.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable(){
                                public void run() {
                                    wrongImage3.setVisibility(View.INVISIBLE);
                                }
                            }, 600);
                        }
                        updateCurrentScore();
                        decreaseItems();
                        animateToOrigin(view);
                    }
                    else if (isViewOverlapping(view,organicBin,"organic")) {
                        System.out.println("ORGANIC BIN");
                        if (currentGarbageItem.garbageType == GarbageType.ORGANIC) {
                            increaseScore();
                            correctImage4.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable(){
                                public void run() {
                                    correctImage4.setVisibility(View.INVISIBLE);
                                }
                            }, 600);
                        }
                        else {
                            wrongImage4.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable(){
                                public void run() {
                                    wrongImage4.setVisibility(View.INVISIBLE);
                                }
                            }, 600);
                        }
                        updateCurrentScore();
                        decreaseItems();
                        animateToOrigin(view);
                    }
                    else {
                        animateToOrigin(view);
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                    view.animate()
                            .x( event.getRawX() + dX )
                            .y(event.getRawY() + dY)
                            .setDuration(0)
                            .start();

                    break;
                default:
                    return false;
            }
            return true;
        }
        private void animateToOrigin(View view) {
            view.animate()
                    .x(originalX)
                    .y(originalY)
                    .setDuration(100)
                    .start();
        }
    }


    private void increaseScore() {
        currentScore++;
    }
    private void decreaseItems()
    {
        items--;
        itemsLeft.setText("Items: "+items);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        stopTimer();
                        Toast.makeText(getApplicationContext(),"See you soon",
                                Toast.LENGTH_SHORT).show();
                        GameActivity.this.finish();
                        Intent intent = new Intent(GameActivity.this, StartGameActivity.class);
                        startActivity(intent);
                    }
                }).create().show();
    }

    private boolean isViewOverlapping(View firstView, View secondView, String name) {
        int[] firstPosition = new int[2];
        int[] secondPosition = new int[2];
        firstView.getLocationOnScreen(firstPosition);
        secondView.getLocationOnScreen(secondPosition);
        int firstViewX = firstPosition[0];
        int firstViewY = firstPosition[1];
        int secondViewX = secondPosition[0];
        int secondViewY = secondPosition[1];
        int firstViewRight = firstViewX + firstView.getWidth();
        int firstViewBottom = firstViewY + firstView.getHeight();
        int secondViewRight = secondViewX + secondView.getWidth();
        int secondViewBottom = secondViewY + secondView.getHeight();
        Rect r1 = new Rect(firstViewX, firstViewY, firstViewRight, firstViewBottom);
        Rect r2 = new Rect(secondViewX, secondViewY, secondViewRight, secondViewBottom);
        return Rect.intersects(r1,r2);

    }

    public class GetScoreAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return RestAPIClient.showScore();
        }

        @Override
        protected void onPostExecute(String response) {
            try {
                JSONObject result = new JSONObject(response);
                JSONArray resultArray = result.getJSONArray("results");
                for (int i = 0; i < resultArray.length(); i++) {
                    JSONObject obj = resultArray.getJSONObject(i);
                    String email = obj.getString("Email");
                    if (userEmail.matches(email)) {
                        String score = obj.getString("Highscore");
                        highScore = Integer.parseInt(score);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public class UpdateScoreAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestAPIClient.updateScore(params[0],Integer.valueOf(params[1]));
        }

        @Override
        protected void onPostExecute(String response) {
            System.out.println("The response is: "+ response);
        }
    }

}


