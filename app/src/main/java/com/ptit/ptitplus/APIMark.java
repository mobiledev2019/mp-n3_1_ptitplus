package com.ptit.ptitplus;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APIMark {
    private static final String URL_BASE = "http://192.168.1.14:3000/point?";
    private static final String PARAMATER = "msv";
    public static String getMark(String value){
        String markJsonString = null;
        HttpURLConnection httpURLConnection = null;
        BufferedReader br = null;


        Uri uri = Uri.parse(URL_BASE).buildUpon().appendQueryParameter(PARAMATER, value).build();

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
            markJsonString = stringBuilder.toString();
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

        return markJsonString;
    }
}
