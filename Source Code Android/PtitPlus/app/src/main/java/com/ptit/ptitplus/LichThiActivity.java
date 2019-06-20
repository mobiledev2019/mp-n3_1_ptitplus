package com.ptit.ptitplus;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.ptitplus.asynctaskLoader.LichThiLoader;
import com.ptit.ptitplus.model.LichThi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LichThiActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    private ArrayList<LichThi> listLichThi;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_thi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Lịch thi");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        sharedPreferences = getSharedPreferences(MainActivity.SHAREDPRESIGNIN, MODE_PRIVATE);
        String s = sharedPreferences.getString(MainActivity.KEYLICHTHI, null);
        if (s==null) {
            String user = sharedPreferences.getString(MainActivity.USERNAME, null);
            String pass = sharedPreferences.getString(MainActivity.PASSWORD, null);

            if (LoaderManager.getInstance(this).getLoader(0) != null) {
                LoaderManager.getInstance(this).initLoader(0, null, this);
            }

            Bundle bundle = new Bundle();
            if (user == null) {
                Toast.makeText(this, "Chưa đăng nhập", Toast.LENGTH_LONG).show();
            } else {
                bundle.putString("user", user);
                bundle.putString("pass", pass);
                LoaderManager.getInstance(this).restartLoader(0, bundle, this);
            }
        }else{
            listLichThi = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    LichThi lichThi = new LichThi(jsonObject.getString("stt"), jsonObject.getString("MaMH"), jsonObject.getString("TenMH")
                            , jsonObject.getString("NgayThi"), jsonObject.getString("GioBD"), jsonObject.getString("SoPhut"), jsonObject.getString("Phong")
                            , jsonObject.getString("GhiChu"));
                    listLichThi.add(lichThi);
                }
                addRowIntoTable();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        Log.d("lichthi", "create loader");
        String user = "";
        String pass = "";
        if (bundle != null) {
            user = bundle.getString("user");
            pass = bundle.getString("pass");
            return new LichThiLoader(this, user, pass);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        Log.d("lichthi", "finish loader");
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(this, "Please check your internet", Toast.LENGTH_SHORT).show();
            return;
        }
        if (s == null || s.equalsIgnoreCase("[]")) {
            Toast.makeText(this, "Không tìm thấy dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("lichthi", "finish loader: " + s);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.KEYLICHTHI, s);
        editor.apply();
        listLichThi = new ArrayList<>();
        try {
            String string;
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                LichThi lichThi = new LichThi(jsonObject.getString("stt"), jsonObject.getString("MaMH"), jsonObject.getString("TenMH")
                        , jsonObject.getString("NgayThi"), jsonObject.getString("GioBD"), jsonObject.getString("SoPhut"), jsonObject.getString("Phong")
                        , jsonObject.getString("GhiChu"));
                listLichThi.add(lichThi);
            }
            addRowIntoTable();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

        Log.d("lichthi", "reset loader");

    }



    public void addRowIntoTable() {
        for (int i = 0; i < listLichThi.size(); i++) {
            TableRow tableRow = findViewById(getResources().getIdentifier("row"+(i+1),"id", getPackageName()));
            TextView stt = findViewById(getResources().getIdentifier("row"+i+"0", "id", getPackageName()));
            stt.setText(listLichThi.get(i).getStt());
            final TextView mon = findViewById(getResources().getIdentifier("row"+i+"1", "id", getPackageName()));
            mon.setText(listLichThi.get(i).getTenMH());
            final TextView ngay = findViewById(getResources().getIdentifier("row"+i+"2", "id", getPackageName()));
            ngay.setText(listLichThi.get(i).getNgayThi());
            final TextView gio = findViewById(getResources().getIdentifier("row"+i+"3", "id", getPackageName()));
            gio.setText(listLichThi.get(i).getGioBD());
            final TextView phong = findViewById(getResources().getIdentifier("row"+i+"4", "id", getPackageName()));
            phong.setText(listLichThi.get(i).getPhong());
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(LichThiActivity.this,
                            "môn: " + mon.getText().toString() + ", ngày: " + ngay.getText().toString()
                            + ", bắt đầu lúc: " + gio.getText().toString() + ", tại phòng: " + phong.getText().toString()
                            , Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
