package com.ptit.ptitplus.api;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.ptit.ptitplus.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APISchedule {
    private static final String URL_BASE = MainActivity.HOST + "/schedule?";
    private static final String PARAMATER = "msv";
    public static String getTKB(String msv){
        String jsonString = null;
        Uri uri = Uri.parse(URL_BASE).buildUpon().appendQueryParameter(PARAMATER,msv).build();
        HttpURLConnection httpURLConnection = null;
        BufferedReader br = null;

        try {
            URL url =new URL(uri.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream is = httpURLConnection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line=br.readLine())!=null){
                Log.d("abc", line);
                stringBuilder.append(line);
            }
            if (stringBuilder.length() == 0)
                return null;
            jsonString = stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonString;
    }
}
