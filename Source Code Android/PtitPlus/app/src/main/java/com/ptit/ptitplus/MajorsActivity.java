package com.ptit.ptitplus;

import android.content.res.TypedArray;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ptit.ptitplus.adapter.MajorsAdapter;
import com.ptit.ptitplus.model.Majors;

import java.util.ArrayList;

public class MajorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_majors);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle(R.string.thong_tin_nganh);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ArrayList<Majors> listMajors = new ArrayList<>();
        String[] majorsNameArr = getResources().getStringArray(R.array.nganhNameArr);
        TypedArray majorsImage =getResources().obtainTypedArray(R.array.nganhImageArr);
        for (int i = 0; i < majorsNameArr.length; i++){
            listMajors.add(new Majors(majorsNameArr[i], majorsImage.getResourceId(i, 0)));
        }
        majorsImage.recycle();
        MajorsAdapter adapter = new MajorsAdapter(listMajors, MajorsActivity.this);
        RecyclerView recyclerView = findViewById(R.id.majorsRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MajorsActivity.this, LinearLayoutManager.VERTICAL, false));
    }
}
