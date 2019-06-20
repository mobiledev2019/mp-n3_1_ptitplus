package com.ptit.ptitplus.asyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ptit.ptitplus.MainActivity;
import com.ptit.ptitplus.api.APISchedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;
    private Spinner spinner;
    private Spinner tuan;

    public ScheduleAsyncTask(Context context, Spinner spinner, Spinner tuan) {
        this.context = context;
        this.spinner = spinner;
        this.tuan = tuan;
    }

    @Override
    protected String doInBackground(String... strings) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.PRENAME, Context.MODE_PRIVATE);
        String s = sharedPreferences.getString(MainActivity.KEY, null);
        if (s != null) {
            return s;
        }
        return APISchedule.getTKB(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.PRENAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(MainActivity.KEY, s);
            editor.apply();
            try {
                Calendar now = Calendar.getInstance();
                JSONArray kyArr = new JSONArray(s);
                ArrayList<String> arrayList = new ArrayList<>();
                int kyNow = kyArr.length() - 1;
                for (int i = 0; i < kyArr.length(); i++) {
                    arrayList.add(kyArr.getJSONObject(i).getString("ky"));
                }
                for (int i = kyArr.length() - 1; i >= 0; i--) {
                    String date = kyArr.getJSONObject(i).getString("start");
                    int ngay = Integer.parseInt(date.split("/")[0]);
                    int thang = Integer.parseInt(date.split("/")[1]);
                    int nam = Integer.parseInt(date.split("/")[2]);
                    Calendar tmp = Calendar.getInstance();
                    tmp.set(nam, thang - 1, ngay);
                    if (now.getTimeInMillis() > tmp.getTimeInMillis()) {
                        kyNow = i;
                        break;
                    }
                }
                String start = kyArr.getJSONObject(kyNow).getString("start");
                JSONArray monArr = kyArr.getJSONObject(kyNow).getJSONArray("mon");
                int soTuan = 1;
                for (int i = 0; i < monArr.length(); i++) {
                    if (monArr.getJSONObject(i).getString("Tuan").length() > soTuan)
                        soTuan = monArr.getJSONObject(i).getString("Tuan").length();
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                int year = Integer.parseInt(start.split("/")[2]);
                int month = Integer.parseInt(start.split("/")[1]) - 1;
                int day = Integer.parseInt(start.split("/")[0]);

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                long ngay7 = 1000 * 60 * 60 * 24 * 7;

                ArrayList<String> arrTuan = new ArrayList<>();
                String timeArr[] = new String[soTuan];
                for (int i = 0; i < soTuan; i++) {
                    String tmp = "Tuan " + (i + 1) + " ";
                    tmp += simpleDateFormat.format(calendar.getTime());
                    timeArr[i] = simpleDateFormat.format(calendar.getTime());
                    arrTuan.add(tmp);
                    calendar.setTimeInMillis(calendar.getTimeInMillis() + ngay7);
                }
                int tuanNow = soTuan - 1;
                for (int i = soTuan - 2; i >= 0; i--) {
                    int ngay = Integer.parseInt(timeArr[i].split("/")[0]);
                    int thang = Integer.parseInt(timeArr[i].split("/")[1]);
                    int nam = Integer.parseInt(timeArr[i].split("/")[2]);
                    Calendar tmp = Calendar.getInstance();
                    tmp.set(nam, thang - 1, ngay);
                    if (now.getTimeInMillis() > tmp.getTimeInMillis()) {
                        tuanNow = i;
                        break;
                    }
                }
                ArrayAdapter<String> arrayAdapterTuan = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrTuan);
                arrayAdapterTuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                tuan.setAdapter(arrayAdapterTuan);
                tuan.setSelection(tuanNow);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setSelection(kyNow);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.PRENAME, Context.MODE_PRIVATE);
            String tkb = sharedPreferences.getString(MainActivity.KEY, null);
            if (tkb == null) {
                Toast.makeText(context, "Hệ thống đang bảo trì", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                Calendar now = Calendar.getInstance();
                JSONArray kyArr = new JSONArray(tkb);
                ArrayList<String> arrayList = new ArrayList<>();
                int kyNow = kyArr.length() - 1;
                for (int i = 0; i < kyArr.length(); i++) {
                    arrayList.add(kyArr.getJSONObject(i).getString("ky"));
                }
                for (int i = kyArr.length() - 1; i >= 0; i--) {
                    String date = kyArr.getJSONObject(i).getString("start");
                    int ngay = Integer.parseInt(date.split("/")[0]);
                    int thang = Integer.parseInt(date.split("/")[1]);
                    int nam = Integer.parseInt(date.split("/")[2]);
                    Calendar tmp = Calendar.getInstance();
                    tmp.set(nam, thang - 1, ngay);
                    if (now.getTimeInMillis() > tmp.getTimeInMillis()) {
                        kyNow = i;
                        break;
                    }
                }
                String start = kyArr.getJSONObject(kyNow).getString("start");
                JSONArray monArr = kyArr.getJSONObject(kyNow).getJSONArray("mon");
                int soTuan = 1;
                for (int i = 0; i < monArr.length(); i++) {
                    if (monArr.getJSONObject(i).getString("Tuan").length() > soTuan)
                        soTuan = monArr.getJSONObject(i).getString("Tuan").length();
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                int year = Integer.parseInt(start.split("/")[2]);
                int month = Integer.parseInt(start.split("/")[1]) - 1;
                int day = Integer.parseInt(start.split("/")[0]);

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                long ngay7 = 1000 * 60 * 60 * 24 * 7;

                ArrayList<String> arrTuan = new ArrayList<>();
                String timeArr[] = new String[soTuan];
                for (int i = 0; i < soTuan; i++) {
                    String tmp = "Tuan " + (i + 1) + " ";
                    tmp += simpleDateFormat.format(calendar.getTime());
                    timeArr[i] = simpleDateFormat.format(calendar.getTime());
                    arrTuan.add(tmp);
                    calendar.setTimeInMillis(calendar.getTimeInMillis() + ngay7);
                }
                int tuanNow = soTuan - 1;
                for (int i = soTuan - 1; i >= 0; i--) {
                    int ngay = Integer.parseInt(timeArr[i].split("/")[0]);
                    int thang = Integer.parseInt(timeArr[i].split("/")[1]);
                    int nam = Integer.parseInt(timeArr[i].split("/")[2]);
                    Calendar tmp = Calendar.getInstance();
                    tmp.set(nam, thang - 1, ngay);
                    if (now.getTimeInMillis() > tmp.getTimeInMillis()) {
                        tuanNow = i;
                        break;
                    }
                }

                ArrayAdapter<String> arrayAdapterTuan = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrTuan);
                arrayAdapterTuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                tuan.setAdapter(arrayAdapterTuan);
                tuan.setSelection(tuanNow);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setSelection(kyNow);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
