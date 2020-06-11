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

import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {
    EditText et_email,et_password,et_cpassword;
    Button btn_submit;
    String r_email,r_password;
    TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);
        getSupportActionBar().setTitle("Sign Up");
        registerText = (TextView) findViewById(R.id.registerText);
        registerText.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
        et_email = (EditText) findViewById(R.id.et_email1);
        et_email.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
        et_password = (EditText) findViewById(R.id.et_password1);
        et_password.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
        et_cpassword = (EditText) findViewById(R.id.et_cpassword);
        et_cpassword.startAnimation(AnimationUtils.loadAnimation(this,R.anim.righttoleft));
        btn_submit = (Button) findViewById(R.id.btn_register);
        btn_submit.startAnimation(AnimationUtils.loadAnimation(this,R.anim.lefttoright));
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                r_email = et_email.getText().toString();
                r_password = et_password.getText().toString();
                String r_cpassword = et_cpassword.getText().toString();
                if(r_email.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$")) {
                        if (!r_password.equals("") && !r_cpassword.equals("")) {
                            if (r_password.equals(r_cpassword)) {
                                LoginActivity login = new LoginActivity();
                                String r_hashPassword = login.passwordHash(r_password);
                                SignUpAsyncTask signUpTask = new SignUpAsyncTask();
                                signUpTask.execute(r_email, r_hashPassword);
                            } else {
                                Toasty.error(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                            }
                        }
                            else
                            {
                                Toasty.error(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                            }
                } else {
                    Toasty.error(getApplicationContext(), "Username has to be combination of alphabets and digits", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        public class SignUpAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                return RestAPIClient.addUser(params[0],params[1]);
            }

            @Override
            protected void onPostExecute(String response) {
                try{
                    JSONObject result = new JSONObject(response);
                    String status = result.getString("success");
                    String message = result.getString("message");
                    if(status.matches("true"))
                    {
                        SharedPreferences sp = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("isUserLogin", true);
                        editor.putString("email",r_email);
                        editor.apply();
                        Toasty.success(getApplicationContext(),"Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent welcomeIntent = new Intent(SignUpActivity.this,StartGameActivity.class);
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
    }

