package com.gloriousfour.binlet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class LeaderboardActivity extends AppCompatActivity {
    ListView userList;
    TextView txt1,txt2,txt3;
    ArrayList<User> theList = new ArrayList<>();
    String usremail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        getSupportActionBar().setTitle("Leaderboard");
        SharedPreferences sp = getApplicationContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        usremail = sp.getString("email",null);
        txt1 =(TextView) findViewById(R.id.txt1);
        txt2 =(TextView) findViewById(R.id.txt2);
        txt3 =(TextView) findViewById(R.id.txt3);
        userList = (ListView) findViewById(R.id.list_view);
        LeaderBoardAsyncTask topTen = new LeaderBoardAsyncTask();
        topTen.execute();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.leaderboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.game:
                        startActivity(new Intent(getApplicationContext(), StartGameActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.quiz:
                        startActivity(new Intent(getApplicationContext(), SelectQuizActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.leaderboard:
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    public class LeaderBoardAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return RestAPIClient.showScore();
        }
        @Override
        protected void onPostExecute(String response) {
            try
            {
                JSONObject result = new JSONObject(response);
                JSONArray resultArray = result.getJSONArray("results");
                for(int i = 0; i < resultArray.length(); i++) {
                    JSONObject obj = resultArray.getJSONObject(i);
                    String email = obj.getString("Email");
                    String highScore = obj.getString("Highscore");
                    int id=i+1;
                    User user = new User(id,email,highScore);
                        if (i <= 2) {
                            switch (i + 1) {
                                case 1:
                                    txt1.setText(id+". "+ email + "\n" + highScore + " " + "Points");
                                    txt1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate));
                                    break;
                                case 2:
                                    txt2.setText(id+". "+ email + "\n" + highScore + " " + "Points");
                                    txt2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate));
                                    break;
                                case 3:
                                    txt3.setText(id+". "+ email + "\n" + highScore + " " + "Points");
                                    txt3.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate));
                                    break;
                            }
                            continue;
                        }
                        else
                        {
                            if(i<=9) {
                                theList.add(user);
                                UserListAdapter adapter = new UserListAdapter(getApplicationContext(), R.layout.list_view, theList);
                                userList.setAdapter(adapter);
                            }
                            else
                            {
                                if(!highScore.matches("0")) {
                                    if (email.matches(usremail)) {
                                        final Toast toast = Toasty.info(getApplicationContext(), "HIGH SCORE: " + highScore + " " + "AND" + " " + "RANK: " + id, Toast.LENGTH_LONG);
                                        toast.show();
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                toast.cancel();
                                            }
                                        }, 4000);
                                    }

                                }
                                else {
                                    if (email.matches(usremail)) {
                                        Toasty.info(getApplicationContext(), "You haven't played this game yet", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(LeaderboardActivity.this, StartGameActivity.class);
        startActivity(intent);
    }
}

