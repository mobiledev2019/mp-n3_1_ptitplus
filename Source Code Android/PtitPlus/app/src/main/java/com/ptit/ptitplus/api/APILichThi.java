package com.ptit.ptitplus.api;

import android.net.Uri;

import com.ptit.ptitplus.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APILichThi {
    private static final String URL_BASE = MainActivity.HOST + "/lichThi?";
    private static final String PARAMATER1 = "user";
    private static final String PARAMATER2 = "pass";
    public static String getLichThi(String user, String pass){
        String accountJsonString = null;
        HttpURLConnection httpURLConnection = null;
        BufferedReader br = null;


        Uri uri = Uri.parse(URL_BASE).buildUpon().appendQueryParameter(PARAMATER1, user)
                .appendQueryParameter(PARAMATER2, pass)
                .build();

        try {
            URL url = new URL(uri.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream is = httpURLConnection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line=br.readLine())!=null){
                stringBuilder.append(line);
            }
            if (stringBuilder.length() == 0)
                return null;
            accountJsonString = stringBuilder.toString();
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

        return accountJsonString;
    }
}
