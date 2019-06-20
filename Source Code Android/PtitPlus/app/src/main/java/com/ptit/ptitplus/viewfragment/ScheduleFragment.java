package com.ptit.ptitplus.viewfragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ptit.ptitplus.InfoTeacherActivity;
import com.ptit.ptitplus.MainActivity;
import com.ptit.ptitplus.R;
import com.ptit.ptitplus.asyncTask.ScheduleAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {
    public Spinner ky;
    public  Spinner tuan;
    private int positionKy;
    private int positionTuan;
    private int soTuan;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ky = view.findViewById(R.id.ky);
        tuan = view.findViewById(R.id.tuan);
        ky.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionKy = position;
                showTuan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionTuan = position;
                showMonHoc();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MainActivity.SHAREDPRESIGNIN, MODE_PRIVATE);
        String id = sharedPreferences.getString(MainActivity.USERNAME, null);
        if (id != null) {
            String msv[] = {id};
            ScheduleAsyncTask task = new ScheduleAsyncTask(getContext(), ky, tuan);
            task.execute(msv);
        }else{
            Toast.makeText(getContext(), "Bạn chưa đăng nhập", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    public void showKy(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MainActivity.PRENAME, MODE_PRIVATE);
        String jsonString = sharedPreferences.getString(MainActivity.KEY, null);
        if (jsonString == null){
            return;
        }
        JSONArray jsonArrayKy = null;
        try {
            jsonArrayKy = new JSONArray(jsonString);
            ArrayList<String> arrayListKy = new ArrayList<>();
            for (int i = 0; i < jsonArrayKy.length(); i++){
                arrayListKy.add(jsonArrayKy.getJSONObject(i).getString("ky"));
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayListKy);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ky.setAdapter(arrayAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showTuan(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MainActivity.PRENAME, MODE_PRIVATE);
        String jsonString = sharedPreferences.getString(MainActivity.KEY, null);
        if (jsonString == null){
            return;
        }
        try {
            JSONArray arrayKy = new JSONArray(jsonString);
            JSONObject objectKy = arrayKy.getJSONObject(positionKy);
            JSONArray arrayMon = objectKy.getJSONArray("mon");
            String startDate = objectKy.getString("start");
            soTuan = 1;
            for (int i = 0; i < arrayMon.length(); i++){
                JSONObject objectMon = arrayMon.getJSONObject(i);
                if (objectMon.getString("Tuan").length() > soTuan)
                    soTuan = objectMon.getString("Tuan").length();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            int year = Integer.parseInt(startDate.split("/")[2]);
            int month = Integer.parseInt(startDate.split("/")[1])-1;
            int day = Integer.parseInt(startDate.split("/")[0]);

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            long ngay7 =  1000*60*60*24*7;

            ArrayList<String> arrTuan = new ArrayList<>();
            String timeArr[] = new String[soTuan];
            for (int i = 0; i < soTuan; i++){
                String tmp = "Tuan " + (i + 1)+" ";
                tmp += simpleDateFormat.format(calendar.getTime());
                timeArr[i] = simpleDateFormat.format(calendar.getTime());
                arrTuan.add(tmp);
                calendar.setTimeInMillis(calendar.getTimeInMillis() + ngay7);
            }
            Calendar now = Calendar.getInstance();
            int tuanNow = soTuan-1;
            for (int i = soTuan-1; i >=0; i--){
                int ngay = Integer.parseInt(timeArr[i].split("/")[0]);
                int thang = Integer.parseInt(timeArr[i].split("/")[1]);
                int nam = Integer.parseInt(timeArr[i].split("/")[2]);
                Calendar tmp = Calendar.getInstance();
                tmp.set(nam, thang-1, ngay);
                if (now.getTimeInMillis() > tmp.getTimeInMillis()){
                    tuanNow = i;
                    break;
                }
            }
            ArrayAdapter<String> arrayAdapterTuan = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrTuan);
            arrayAdapterTuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            tuan.setAdapter(arrayAdapterTuan);
            tuan.setSelection(tuanNow);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showMonHoc(){
        init();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MainActivity.PRENAME, MODE_PRIVATE);
        String jsonString = sharedPreferences.getString(MainActivity.KEY, null);
        if (jsonString == null){
            return;
        }
        try {
            JSONArray arrayMon = new JSONArray(jsonString).getJSONObject(positionKy).getJSONArray("mon");
            for (int i = 0; i < arrayMon.length(); i++){
                final JSONObject objectMon = arrayMon.getJSONObject(i);
                String lich = objectMon.getString("Tuan");
                String tmp;
                try{
                    tmp = lich.charAt(positionTuan)+"";
                }catch (Exception e){
                    tmp = "";
                }
                if (!(tmp.equalsIgnoreCase("")||tmp.equalsIgnoreCase("-"))){
                    String kip = "kip"+((Integer.parseInt(objectMon.getString("TietBD"))/2)+1);
                    String thu = "thu";
                    switch (objectMon.getString("Thu")){
                        case "Hai":
                            thu+="2";
                            break;
                        case "Ba":
                            thu+="3";
                            break;
                        case "Tư":
                            thu+="4";
                            break;
                        case "Năm":
                            thu+="5";
                            break;
                        case "Sáu":
                            thu+="6";
                            break;
                        case "Bảy":
                            thu+="7";
                            break;
                        default:
                            thu = "chunhat";
                            break;

                    }
                    String id = thu+kip;
                    final String ten = objectMon.getString("TenMH");
                    final String phong = objectMon.getString("Phong");
                    final String nhom = objectMon.getString("NMH");
                    final Context context = getContext();
                    TextView textView = getView().findViewById(getResources().getIdentifier(id, "id", getContext().getPackageName()));
                    textView.setText(ten);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "Môn: " + ten+", Phòng: "+phong + ", Nhóm: " +nhom, Toast.LENGTH_LONG).show();
                        }
                    });
                    textView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Intent intent = new Intent(getContext(), InfoTeacherActivity.class);
                            intent.putExtra("mon", ten);
                            startActivity(intent);
                            return true;
                        }
                    });
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        for (int i = 2; i < 8; i++){
            for (int j = 1; j < 7; j++){
                String id = "thu"+i+"kip"+j;
                TextView textView = getView().findViewById(getResources().getIdentifier(id, "id", getContext().getPackageName()));
                textView.setText("");
            }
        }
    }

}
