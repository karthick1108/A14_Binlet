package com.gloriousfour.binlet;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class RestAPIClient {
    private static final String BASE_URL = "https://www.binlet.me/";

    // To fetch data for existing user
    public static String getUser(String email, String password) {
        URL url = null;
        HttpURLConnection conn = null;
        String response = "";
        final String methodPath="login/";
        try {
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("email", email);
            params.put("password", password);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                System.out.println("postData:"+postData);
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            url = new URL(BASE_URL + methodPath);
            System.out.print("The URL is "+url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.getOutputStream().write(postDataBytes);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            response = sb.toString();
            System.out.print(Log.d("error",response));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            conn.disconnect();
        }
        System.out.println("Outcome is: "+response);
        return response;
    }

    // New user Registration
    public static String addUser(String email, String password){
        URL url = null;
        HttpURLConnection conn = null;
        String response = "";
        final String methodPath="register/";
        try {
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("email", email);
            params.put("password", password);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                System.out.println("postData:"+postData);
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            url = new URL(BASE_URL + methodPath);
            System.out.print("The URL is "+url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.getOutputStream().write(postDataBytes);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            response = sb.toString();
            System.out.print(Log.d("error",response));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            conn.disconnect();
        }
        return response;
    }

    //Show Score in the leaderboard
    public static String showScore() {
        final String methodPath = "showScore/";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        //System.out.println("showScore response: "+ textResult);
        return textResult;

    }


    // Update Score if user beats his own best
    public static String updateScore(String email, int highscore){
        URL url = null;
        HttpURLConnection conn = null;
        String response = "";
        final String methodPath="updateScore/";
        try {
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("email", email);
            params.put("highscore", highscore);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                System.out.println("postData:"+postData);
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            url = new URL(BASE_URL + methodPath);
            System.out.print("The URL is "+url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.getOutputStream().write(postDataBytes);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            response = sb.toString();
            System.out.print(Log.d("error",response));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            conn.disconnect();
        }
        return response;
    }

}
