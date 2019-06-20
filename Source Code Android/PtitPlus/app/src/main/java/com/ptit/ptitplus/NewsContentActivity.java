package com.ptit.ptitplus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ptit.ptitplus.asyncTask.NewsContentAsyncTask;

public class NewsContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.thong_bao);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        WebView webView = findViewById(R.id.newsContent);

        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHAREDPRENEWS, MODE_PRIVATE);
        String data = sharedPreferences.getString(link, null);
        if (data == null) {
            NewsContentAsyncTask task = new NewsContentAsyncTask(NewsContentActivity.this, webView);
            task.execute(link);
        } else{
            webView.loadData(data, "text/html", "utf-8");
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

        }
    }
}
