package com.ptit.ptitplus.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ptit.ptitplus.adapter.CMTAdapter;
import com.ptit.ptitplus.api.APISelect;
import com.ptit.ptitplus.model.CMT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectAsyncTask extends AsyncTask<String, Void, String> {
    Context context;
    CMTAdapter adapter;
    ArrayList<CMT> listCMT;
    public SelectAsyncTask(Context context, CMTAdapter adapter, ArrayList<CMT> listCMt) {
        this.context = context;
        this.listCMT = listCMt;
        this.adapter = adapter;
    }

    @Override
    protected String doInBackground(String... strings) {
        return APISelect.getCMT(strings[0], strings[1]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("select", s);
        try {
            JSONArray jsonArray = new JSONArray(s);
            if (jsonArray.length() != 0){
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject cmt = jsonArray.getJSONObject(i);
                    listCMT.add(new CMT(cmt.getString("sv"),cmt.getString("cmt")));
                }
            }
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
