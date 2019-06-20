package com.ptit.ptitplus.asyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ptit.ptitplus.MainActivity;
import com.ptit.ptitplus.api.APIInfoTeacher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InfoTeacherAsyncTask extends AsyncTask<String, Void, String> {
    Spinner gv;
    Spinner mon;
    Context context;

    public InfoTeacherAsyncTask(Spinner gv, Spinner mon, Context context) {
        this.gv = gv;
        this.mon = mon;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        return APIInfoTeacher.getInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.SHAREDPREINFOTEACHER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.KEYINFOTEACHER, s);
        editor.apply();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArrayMon = jsonObject.getJSONArray("mon");
            JSONArray jsonArrayGV = jsonObject.getJSONArray("gv");
            ArrayList<String> arrayListMon = new ArrayList<>();
            ArrayList<String> arrayListGV = new ArrayList<>();
            for (int i = 0; i < jsonArrayMon.length(); i++){
                arrayListMon.add(jsonArrayMon.getJSONObject(i).getString("ten"));
            }
            for (int i = 0; i < jsonArrayGV.length(); i++){
                arrayListGV.add(jsonArrayGV.getJSONObject(i).getString("ten"));
            }
            ArrayAdapter<String> adapterMon = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrayListMon);
            ArrayAdapter<String> adapterGV = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrayListGV);
            adapterGV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapterMon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            mon.setAdapter(adapterMon);
            gv.setAdapter(adapterGV);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
