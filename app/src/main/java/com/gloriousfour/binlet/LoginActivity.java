package com.gloriousfour.binlet;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    EditText UserEmail, UserPassword;
    Button register,login;
    String email,password;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        getSupportActionBar().setTitle("Login");
        welcome = (TextView) findViewById(R.id.welcome);
        welcome.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
        UserEmail = (EditText) findViewById(R.id.et_email);
        UserEmail.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
        UserPassword = (EditText) findViewById(R.id.et_password);
        UserPassword.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
        register = findViewById(R.id.b_register);
        register.startAnimation(AnimationUtils.loadAnimation(this,R.anim.lefttoright));
        login = (Button) findViewById(R.id.b_login);
        login.startAnimation(AnimationUtils.loadAnimation(this,R.anim.lefttoright));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = UserEmail.getText().toString();
                password = UserPassword.getText().toString();
                if(!email.equals("")) {
                    if (!password.equals("")) {
                        String hashPassword = passwordHash(password);
                        LoginAsyncTask loginTask = new LoginAsyncTask();
                        loginTask.execute(email, hashPassword);
                    } else {
                        Toasty.error(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toasty.error(getApplicationContext(),"Please enter your Username", Toast.LENGTH_SHORT).show();
                }
            }
        });
       register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    public String passwordHash(final String password) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public class LoginAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestAPIClient.getUser(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String success) {
            try{
                JSONObject result = new JSONObject(success);
                    String status = result.getString("success");
                    String message = result.getString("message");
                    if(status.matches("true"))
                    {
                        SharedPreferences sp = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("isUserLogin", true);
                        editor.putString("email",email);
                        editor.apply();
                        Toasty.success(getApplicationContext(),"Login Successful", Toast.LENGTH_SHORT).show();
                        Intent welcomeIntent = new Intent(LoginActivity.this,StartGameActivity.class);
                        startActivity(welcomeIntent);
                    }
                    else
                    {
                        Toasty.error(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(LoginActivity.this, LandingScreenActivity.class);
        startActivity(intent);
    }
}
