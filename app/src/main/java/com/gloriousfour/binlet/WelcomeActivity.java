package com.gloriousfour.binlet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    String email;
    PieChart pieChart;
    ImageView imgYellow,imgGreen,imgBlack,imgOrange,home_image1;
    String bin;
    Button recycle,reuse,reduce;
    TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().setTitle("Information");
        //home_image1 = (ImageView) findViewById(R.id.home_image1);
        //home_image1.startAnimation(AnimationUtils.loadAnimation(this,R.anim.animate));
        pieChart = (PieChart) findViewById(R.id.piechart);
        imgYellow = (ImageView) findViewById(R.id.imgYellow);
        imgGreen = (ImageView) findViewById(R.id.imgGreen);
        imgBlack = (ImageView) findViewById(R.id.imgBlack);
        imgOrange = (ImageView) findViewById(R.id.imgOrange);
        recycle = (Button) findViewById(R.id.home_button1);
        reuse = (Button) findViewById(R.id.home_button2);
        reduce = (Button) findViewById(R.id.home_button3);
        headerText = (TextView) findViewById(R.id.headerText);
        headerText.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
        YouTubePlayerView youTubePlayerView = findViewById(R.id.webView);
        //youTubePlayerView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.bounce));
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "OasbYWF4_S8";
                youTubePlayer.loadVideo(videoId, 0f);
            }
        });
        recycle.startAnimation(AnimationUtils.loadAnimation(this,R.anim.bounce));
        reuse.startAnimation(AnimationUtils.loadAnimation(this,R.anim.bounce));
        reduce.startAnimation(AnimationUtils.loadAnimation(this,R.anim.bounce));
        displayPieChart();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
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
                        Intent accountIntent = new Intent(getApplicationContext(),AccountActivity.class);
                        startActivity(accountIntent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        imgYellow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent yellowIntent = new Intent(WelcomeActivity.this,ShowInfoActivity.class);
                bin ="Yellow";
                yellowIntent.putExtra("BIN",bin);
                startActivity(yellowIntent);
            }
        });

        imgGreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bin ="Green";
                Intent greenIntent = new Intent(WelcomeActivity.this,ShowInfoActivity.class);
                greenIntent.putExtra("BIN",bin);
                startActivity(greenIntent);
            }
        });

        imgBlack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bin ="Black";
                Intent blackIntent = new Intent(WelcomeActivity.this,ShowInfoActivity.class);
                blackIntent.putExtra("BIN",bin);
                startActivity(blackIntent);
            }
        });

        imgOrange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bin ="Orange";
                Intent orangeIntent = new Intent(WelcomeActivity.this,ShowInfoActivity.class);
                orangeIntent.putExtra("BIN",bin);
                startActivity(orangeIntent);
            }
        });

        recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                builder.setTitle("Recycle");
                builder.setMessage(R.string.home_recycle_dialog);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });

        reuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                builder.setTitle("Reuse");
                builder.setMessage(R.string.home_reuse_dialog);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });

        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                builder.setTitle("Reduce");
                builder.setMessage(R.string.home_reduce_dialog);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
    }

    private void displayPieChart() {
        List<PieEntry> entries = new ArrayList<>();
        Typeface mTypeFace = Typeface.createFromAsset(this.getAssets(), "regular.ttf");
        entries.add(new PieEntry(48462, "Plastic"));
        entries.add(new PieEntry(287576, "Paper"));
        entries.add(new PieEntry(170403, "Glass"));
        entries.add(new PieEntry(447278, "Organic"));
        PieDataSet set = new PieDataSet(entries,"");
        PieData data = new PieData(set);
        data.setValueTypeface(mTypeFace);
        set.setValueTextSize(15f);
        final int[] MY_COLORS = {Color.rgb(155,135,12), Color.rgb(30,144,255), Color.rgb(205, 59, 59),Color.rgb(0, 128, 0)};
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : MY_COLORS) colors.add(c);
        set.setColors(colors);
        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Recovered Waste in Tonnes");
        pieChart.animateXY(5000, 5000);
        pieChart.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(WelcomeActivity.this, StartGameActivity.class);
        startActivity(intent);
    }
}
