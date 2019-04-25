package com.ptit.ptitplus;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private final int NEWS_FRAGMENT = 1;
    private final int SCHEDULE_FRAGMENT = 2;
    private final int MARK_FRAGMENT = 3;
    private final int INFO_FRAGMENT = 4;
    private int fragment;
    private DrawerLayout drawer;
    private BottomNavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadFragment(Fragment fragment){
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
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.bottom_navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadFragment(new NewsFragment());
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        final ActionBar actionbar = getSupportActionBar();
        if (savedInstanceState!=null){
            fragment = savedInstanceState.getInt("fragment");
            initFragment(actionbar);
        }

        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setTitle(R.string.news);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
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
    }

    public void initFragment(ActionBar actionbar){
        switch (fragment){
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
}
