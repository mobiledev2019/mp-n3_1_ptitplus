package com.ptit.ptitplus;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.ptit.ptitplus.receiver.NotificationReceiver;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {

    Switch swithNotify;
    Switch switchData;
    RadioButton radioButtonAny;
    RadioButton radioButtonWifi;
    RadioGroup radioGroup;
    SharedPreferences.Editor editor;
    boolean notify;
    boolean data;
    boolean wifi;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        swithNotify = findViewById(R.id.switchNotify);
        switchData = findViewById(R.id.switchData);
        radioButtonAny = findViewById(R.id.radioAny);
        radioButtonWifi = findViewById(R.id.radioWifi);
        radioGroup = findViewById(R.id.radioGroup);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Setting");

        final SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHAREDPRESETTING, MODE_PRIVATE);
        notify = sharedPreferences.getBoolean(MainActivity.KEYNOTIFI, true);
        data = sharedPreferences.getBoolean(MainActivity.KEYDATA, true);
        wifi = sharedPreferences.getBoolean(MainActivity.KEYISWIFI, false);
        editor = sharedPreferences.edit();
        if (!data) {
            radioGroup.setVisibility(View.INVISIBLE);
        } else {
            radioGroup.setVisibility(View.VISIBLE);
        }
        if (wifi) {
            radioButtonWifi.setChecked(true);
        } else {
            radioButtonAny.setChecked(true);
        }

        switchData.setChecked(data);
        swithNotify.setChecked(notify);
        swithNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean(MainActivity.KEYNOTIFI, isChecked);
                editor.apply();
                notify = sharedPreferences.getBoolean(MainActivity.KEYNOTIFI, true);
                data = sharedPreferences.getBoolean(MainActivity.KEYDATA, true);
                wifi = sharedPreferences.getBoolean(MainActivity.KEYISWIFI, false);
            }
        });
        switchData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean(MainActivity.KEYDATA, isChecked);
                editor.apply();
                if (!isChecked) {
                    radioGroup.setVisibility(View.INVISIBLE);
                } else {
                    radioGroup.setVisibility(View.VISIBLE);
                }
                notify = sharedPreferences.getBoolean(MainActivity.KEYNOTIFI, true);
                data = sharedPreferences.getBoolean(MainActivity.KEYDATA, true);
                wifi = sharedPreferences.getBoolean(MainActivity.KEYISWIFI, false);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radioAny:
                        editor.putBoolean(MainActivity.KEYISWIFI, false);
                        editor.apply();
                        break;
                    case R.id.radioWifi:
                        editor.putBoolean(MainActivity.KEYISWIFI, true);
                        editor.apply();
                        break;

                }
                notify = sharedPreferences.getBoolean(MainActivity.KEYNOTIFI, true);
                data = sharedPreferences.getBoolean(MainActivity.KEYDATA, true);
                wifi = sharedPreferences.getBoolean(MainActivity.KEYISWIFI, false);
            }
        });
    }
}
