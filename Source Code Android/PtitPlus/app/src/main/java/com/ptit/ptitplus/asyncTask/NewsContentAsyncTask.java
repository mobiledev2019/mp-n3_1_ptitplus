package com.ptit.ptitplus.asyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ptit.ptitplus.MainActivity;
import com.ptit.ptitplus.R;
import com.ptit.ptitplus.adapter.NewsAdapter;
import com.ptit.ptitplus.api.APINews;
import com.ptit.ptitplus.api.APINewsContent;
import com.ptit.ptitplus.model.News;
import com.ptit.ptitplus.viewfragment.NewsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsContentAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;
    private String link;
    private WebView webView;

    public NewsContentAsyncTask(Context context,WebView webView){
        this.context = context;
        this.webView = webView;
    }
    @Override
    protected String doInBackground(String... strings) {
        link = strings[0];
        return APINewsContent.getNewsContent(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s==null){
            Toast.makeText(context, "Hệ thống đang bảo trì", Toast.LENGTH_LONG).show();
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.SHAREDPRENEWS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(link, s);
        editor.apply();
        webView.loadData(s, "text/html", "utf-8");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
}
