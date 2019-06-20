package com.ptit.ptitplus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobScheduler;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.ptitplus.model.LichThi;
import com.ptit.ptitplus.receiver.NotificationReceiver;
import com.ptit.ptitplus.viewfragment.InfoFragment;
import com.ptit.ptitplus.viewfragment.MarkFragment;
import com.ptit.ptitplus.viewfragment.NewsFragment;
import com.ptit.ptitplus.viewfragment.ScheduleFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String NGAYSINH = "ngaysinh";
    public static final String LOP = "lop";
    public static final String KHOA = "khoa";
    public static final String SHAREDPREINFOTEACHER = "infoteacher";
    public static final String KEYINFOTEACHER = "keyinfoteacher";
    public static final String SHAREDPRESETTING = "setting";
    public static final String KEYNOTIFI = "notify";
    public static final String KEYDATA = "data";
    public static final String KEYISWIFI = "wifi";
    public static final int JOBID = 0;
    public static final String KEYLICHTHI = "lichthi";
    private final int NEWS_FRAGMENT = 1;
    private final int SCHEDULE_FRAGMENT = 2;
    private final int MARK_FRAGMENT = 3;
    private final int INFO_FRAGMENT = 4;
    private int fragment;
    private DrawerLayout drawer;
    private BottomNavigationView bottomNavigationView;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    public static final String SHAREDPRESIGNIN = "com.ptit.ptitplus";
    //    public static final String HOST = "https://ptit-plus.herokuapp.com";
    public static final String HOST = "http://192.168.1.48:3000";
    public static final String SHAREDPRENEWS = "com.ptit.ptitplus.sharename.news";
    public static final String NEWS = "com.ptit.ptitplus.key.news";
    public static final String ISFIRSTTIME = "isFirstTime";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String PRENAME = "thoikhoabieu";
    public static final String KEY = "tkb";
    private String name;
    TextView textViewName;
    JobScheduler jobScheduler;
    SharedPreferences sp;
    AlarmManager alarmManager;
    SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("fragment", fragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences(SHAREDPRESETTING, MODE_PRIVATE);
        final SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(KEYDATA, sp.getBoolean(KEYDATA, true));
        ed.putBoolean(KEYNOTIFI, sp.getBoolean(KEYNOTIFI, true));
        ed.putBoolean(KEYISWIFI, sp.getBoolean(KEYISWIFI, false));
        ed.apply();

        sharedPreferences = getSharedPreferences(SHAREDPRESIGNIN, MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean(ISFIRSTTIME, true);
        name = sharedPreferences.getString(NAME, "nobody");
        if (isFirstTime) {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        }

        drawer = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        navigationView = findViewById(R.id.nav_view);
        Menu menuNav = navigationView.getMenu();
        final MenuItem signInItem = menuNav.getItem(6);
        final MenuItem signOutItem = menuNav.getItem(5);

        View headerNav = navigationView.getHeaderView(0);
        textViewName = headerNav.findViewById(R.id.name);
        if (!name.equalsIgnoreCase("nobody")) {
            textViewName.setText(name);
            signOutItem.setVisible(true);
            signInItem.setVisible(false);
        } else {
            signOutItem.setVisible(false);
            signInItem.setVisible(true);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadFragment(new NewsFragment());
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        final ActionBar actionbar = getSupportActionBar();
        if (savedInstanceState != null) {
            fragment = savedInstanceState.getInt("fragment");
            initFragment(actionbar);
        }

        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setTitle(R.string.news);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_navigation_news:
                        actionbar.setTitle(R.string.news);
                        fragment = NEWS_FRAGMENT;
                        loadFragment(new NewsFragment());
                        break;
                    case R.id.bottom_navigation_schedule:
                        actionbar.setTitle(R.string.schedule);
                        fragment = SCHEDULE_FRAGMENT;
                        loadFragment(new ScheduleFragment());
                        break;
                    case R.id.bottom_navigation_mark:
                        actionbar.setTitle(R.string.mark);
                        fragment = MARK_FRAGMENT;
                        loadFragment(new MarkFragment());
                        break;
                    case R.id.bottom_navigation_info:
                        actionbar.setTitle(R.string.info);
                        fragment = INFO_FRAGMENT;
                        loadFragment(new InfoFragment());
                        break;
                }
                return true;
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawer.closeDrawers();
                Menu menu = bottomNavigationView.getMenu();
                MenuItem item;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_drawer_news:
                        actionbar.setTitle(R.string.news);
                        fragment = NEWS_FRAGMENT;
                        loadFragment(new NewsFragment());
                        item = menu.findItem(R.id.bottom_navigation_news);
                        item.setChecked(true);
                        break;
                    case R.id.navigation_drawer_schedule:
                        actionbar.setTitle(R.string.schedule);
                        fragment = SCHEDULE_FRAGMENT;
                        loadFragment(new ScheduleFragment());
                        item = menu.findItem(R.id.bottom_navigation_schedule);
                        item.setChecked(true);
                        break;
                    case R.id.navigation_drawer_mark:
                        actionbar.setTitle(R.string.mark);
                        fragment = MARK_FRAGMENT;
                        loadFragment(new MarkFragment());
                        item = menu.findItem(R.id.bottom_navigation_mark);
                        item.setChecked(true);
                        break;
                    case R.id.navigation_drawer_info:
                        actionbar.setTitle(R.string.info);
                        fragment = INFO_FRAGMENT;
                        loadFragment(new InfoFragment());
                        item = menu.findItem(R.id.bottom_navigation_info);
                        item.setChecked(true);
                        break;
                    case R.id.navigation_drawer_signout:
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(USERNAME);
                        editor.remove(PASSWORD);
                        editor.remove(NAME);
                        editor.remove(KEYLICHTHI);
                        editor.apply();
                        signInItem.setVisible(true);
                        signOutItem.setVisible(false);
                        name = sharedPreferences.getString(NAME, "nobody");
                        textViewName.setText(name);
                        SharedPreferences sp = getSharedPreferences(MainActivity.PRENAME, MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();
                        ed.clear();
                        ed.apply();
                        break;
                    case R.id.navigation_drawer_signin:
                        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_drawer_lichThi:
                        Intent intent1 = new Intent(MainActivity.this, LichThiActivity.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });

        settingNotify();
    }

    public void settingNotify() {
        boolean enableNotify = sp.getBoolean(KEYNOTIFI, true);
        String lichThiJson = sharedPreferences.getString(KEYLICHTHI, null);
        if (enableNotify && lichThiJson != null) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            ArrayList<LichThi> listLichThi = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(lichThiJson);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    LichThi lichThi = new LichThi(jsonObject.getString("stt"), jsonObject.getString("MaMH"), jsonObject.getString("TenMH")
                            , jsonObject.getString("NgayThi"), jsonObject.getString("GioBD"), jsonObject.getString("SoPhut"), jsonObject.getString("Phong")
                            , jsonObject.getString("GhiChu"));
                    listLichThi.add(lichThi);
                }
                for (int i = jsonArray.length() - 1; i >= 0; i--) {
                    int ngay = Integer.parseInt(listLichThi.get(i).getNgayThi().split("/")[0]);
                    Log.d("notify", ngay + " " + i);
                    int thang = Integer.parseInt(listLichThi.get(i).getNgayThi().split("/")[1]);
                    Log.d("notify", thang + " " + i);
                    int nam = Integer.parseInt(listLichThi.get(i).getNgayThi().split("/")[2]);
                    Log.d("notify", nam + " " + i);
                    int gio = Integer.parseInt(listLichThi.get(i).getGioBD().split(":")[0]);
                    Log.d("notify", gio + " " + i);
                    int phut = Integer.parseInt(listLichThi.get(i).getGioBD().split(":")[1]);
                    Log.d("notify", phut + " " + i);
                    Calendar calendar = Calendar.getInstance();
                    Calendar calendar1 = Calendar.getInstance();
                    calendar.set(nam, thang - 1, ngay, gio - 1, phut, 0);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy|hh:mm:ss");
                    Log.d("notify", simpleDateFormat.format(calendar.getTime()) + " " + i);
                    Log.d("notify", simpleDateFormat.format(calendar1.getTime()) + " " + i);
                    if (calendar.getTimeInMillis() > calendar1.getTimeInMillis()) {
                        Log.d("notify", "lon hon " + i);
                        Intent intent = new Intent(MainActivity.this, NotificationReceiver.class);
                        intent.putExtra("mon", listLichThi.get(i).getTenMH());
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    } else {
                        Log.d("notify", "nho hon " + i);
                        Intent intent = new Intent(MainActivity.this, NotificationReceiver.class);
                        intent.putExtra("mon", listLichThi.get(i).getTenMH());
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, i, intent, PendingIntent.FLAG_NO_CREATE);
                        if (pendingIntent != null) {
                            Log.d("notify", "nho hon va pendingintent khac null" + i);
                            if (alarmManager != null)
                                alarmManager.cancel(pendingIntent);
                            pendingIntent.cancel();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            if (lichThiJson != null) {
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                ArrayList<LichThi> listLichThi = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(lichThiJson);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        LichThi lichThi = new LichThi(jsonObject.getString("stt"), jsonObject.getString("MaMH"), jsonObject.getString("TenMH")
                                , jsonObject.getString("NgayThi"), jsonObject.getString("GioBD"), jsonObject.getString("SoPhut"), jsonObject.getString("Phong")
                                , jsonObject.getString("GhiChu"));
                        listLichThi.add(lichThi);
                    }
                    for (int i = jsonArray.length() - 1; i >= 0; i--) {
                        int ngay = Integer.parseInt(listLichThi.get(i).getNgayThi().split("/")[0]);
                        Log.d("notify", ngay + " " + i);
                        int thang = Integer.parseInt(listLichThi.get(i).getNgayThi().split("/")[1]);
                        Log.d("notify", thang + " " + i);
                        int nam = Integer.parseInt(listLichThi.get(i).getNgayThi().split("/")[2]);
                        Log.d("notify", nam + " " + i);
                        int gio = Integer.parseInt(listLichThi.get(i).getGioBD().split(":")[0]);
                        Log.d("notify", gio + " " + i);
                        int phut = Integer.parseInt(listLichThi.get(i).getGioBD().split(":")[1]);
                        Log.d("notify", phut + " " + i);
                        Calendar calendar = Calendar.getInstance();
                        Calendar calendar1 = Calendar.getInstance();
                        calendar.set(nam, thang - 1, ngay, gio - 1, phut, 0);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy|hh:mm:ss");
                        Log.d("notify", simpleDateFormat.format(calendar.getTime()) + " " + i);
                        Log.d("notify", simpleDateFormat.format(calendar1.getTime()) + " " + i);
                        Log.d("notify", "nho hon " + i);
                        Intent intent = new Intent(MainActivity.this, NotificationReceiver.class);
                        intent.putExtra("mon", listLichThi.get(i).getTenMH());
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, i, intent, PendingIntent.FLAG_NO_CREATE);
                        if (pendingIntent != null) {
                            Log.d("notify", "nho hon va pendingintent khac null" + i);
                            if (alarmManager != null)
                                alarmManager.cancel(pendingIntent);
                            pendingIntent.cancel();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        settingNotify();
        Log.d("notify", "onrestart");
    }

    @Override
    protected void onDestroy() {
        settingNotify();
        Log.d("notify", "destroy");
        super.onDestroy();
    }

    public void initFragment(ActionBar actionbar) {
        switch (fragment) {
            case NEWS_FRAGMENT:
                actionbar.setTitle(R.string.news);
                loadFragment(new NewsFragment());
                break;
            case SCHEDULE_FRAGMENT:
                actionbar.setTitle(R.string.schedule);
                loadFragment(new ScheduleFragment());
                break;
            case MARK_FRAGMENT:
                actionbar.setTitle(R.string.mark);
                loadFragment(new MarkFragment());
                break;
            case INFO_FRAGMENT:
                actionbar.setTitle(R.string.info);
                loadFragment(new InfoFragment());
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

    }
}
