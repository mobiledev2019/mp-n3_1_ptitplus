package com.ptit.ptitplus;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class MajorsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_majors_detail);
        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        int position = intent.getIntExtra("position",0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Toast.makeText(MajorsDetailActivity.this, title, Toast.LENGTH_LONG).show();

        WebView webView = findViewById(R.id.webViewMajorsDetail);
        webView.loadUrl(MainActivity.HOST+"/majors?majors="+position);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
