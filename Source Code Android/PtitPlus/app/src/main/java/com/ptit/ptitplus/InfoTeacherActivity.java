package com.ptit.ptitplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ptit.ptitplus.adapter.CMTAdapter;
import com.ptit.ptitplus.asyncTask.InfoTeacherAsyncTask;
import com.ptit.ptitplus.asyncTask.InsertAsyncTask;
import com.ptit.ptitplus.asyncTask.SelectAsyncTask;
import com.ptit.ptitplus.model.CMT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InfoTeacherActivity extends AppCompatActivity {

    Spinner gv;
    Spinner mon;
    Spinner khoa;
    TextView textViewGV;
    CMTAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<CMT> listCMT;
    ImageView avatar;
    String monFromTKB;
    EditText editTextCMT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_teacher);
        Toolbar toolbar = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.infoGV);

        Intent intent = getIntent();
        monFromTKB = intent.getStringExtra("mon");

        gv = findViewById(R.id.spinnerGiangVien);
        editTextCMT = findViewById(R.id.editTextComment);
        mon = findViewById(R.id.spinnerMonHoc);
        khoa = findViewById(R.id.spinnerKhoa);
        textViewGV = findViewById(R.id.textviewTenGiangVien);
        recyclerView = findViewById(R.id.recyclerViewComment);
        avatar = findViewById(R.id.imageViewAvatarGV);
        listCMT = new ArrayList<>();
        adapter = new CMTAdapter(listCMT, InfoTeacherActivity.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(InfoTeacherActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        InfoTeacherAsyncTask task = new InfoTeacherAsyncTask(gv, mon, InfoTeacherActivity.this);
        final SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHAREDPRESIGNIN, MODE_PRIVATE);
        String khoaName = sharedPreferences.getString(MainActivity.KHOA, "Công nghệ thông tin");
        if (khoaName != null) {
            String khoaArr[] = {khoaName};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(InfoTeacherActivity.this, android.R.layout.simple_spinner_item, khoaArr);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            khoa.setAdapter(adapter);
            task.execute(khoaArr);
        }

        mon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listCMT.clear();
                adapter.notifyDataSetChanged();
                if (monFromTKB != null){
                    for (int i = 0; i < mon.getCount(); i++){
                        if (monFromTKB.equalsIgnoreCase(mon.getItemAtPosition(i).toString())){
                            mon.setSelection(i);
                            break;
                        }
                    }
                    monFromTKB = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listCMT.clear();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void xemCMT(View view) {
        listCMT.clear();
        adapter.notifyDataSetChanged();
        textViewGV.setText(gv.getSelectedItem().toString());
        SharedPreferences sharedPreferences1 = getSharedPreferences(MainActivity.SHAREDPREINFOTEACHER, MODE_PRIVATE);
        String json = sharedPreferences1.getString(MainActivity.KEYINFOTEACHER, null);
        Log.d("info", json);
        if (json == null) {
            avatar.setImageResource(R.drawable.image);
        } else {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray arrayGV = jsonObject.getJSONArray("gv");
                for (int i = 0; i < arrayGV.length(); i++) {
                    if (gv.getSelectedItem().toString().equalsIgnoreCase(arrayGV.getJSONObject(i).getString("ten"))) {
                        Glide.with(InfoTeacherActivity.this).load(arrayGV.getJSONObject(i).getString("avatar")).into(avatar);
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SelectAsyncTask task = new SelectAsyncTask(InfoTeacherActivity.this, adapter, listCMT);
        String gvStr = gv.getSelectedItem().toString();
        String monStr = mon.getSelectedItem().toString();
        String arr[] = {gvStr, monStr};
        task.execute(arr);
    }

    public void themCMT(View view) {
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        String cmt = editTextCMT.getText().toString().trim();
        if (cmt.equalsIgnoreCase("")){
            Toast.makeText(InfoTeacherActivity.this, "Không được để trống", Toast.LENGTH_LONG).show();
            return;
        }
        InsertAsyncTask task = new InsertAsyncTask(InfoTeacherActivity.this);
        String gvName = gv.getSelectedItem().toString();
        String monName = mon.getSelectedItem().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHAREDPRESIGNIN, MODE_PRIVATE);
        String msv = sharedPreferences.getString(MainActivity.USERNAME, null);
        String arr[] = {gvName, monName, msv, cmt};
        task.execute(arr);
        editTextCMT.setText("");
    }
}
