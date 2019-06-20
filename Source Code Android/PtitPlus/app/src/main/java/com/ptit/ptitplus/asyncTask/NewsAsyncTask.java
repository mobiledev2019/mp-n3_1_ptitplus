package com.ptit.ptitplus.asyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ptit.ptitplus.MainActivity;
import com.ptit.ptitplus.R;
import com.ptit.ptitplus.adapter.NewsAdapter;
import com.ptit.ptitplus.api.APINews;
import com.ptit.ptitplus.model.News;
import com.ptit.ptitplus.viewfragment.NewsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsAsyncTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private ArrayList<News> listNews;
    private NewsAdapter newsAdapter;

    public NewsAsyncTask(Context context, ArrayList<News> listNews, NewsAdapter newsAdapter) {
        this.listNews = listNews;
        this.context = context;
        this.newsAdapter = newsAdapter;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return APINews.getNews();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.SHAREDPRENEWS, Context.MODE_PRIVATE);
            String jsonNews = sharedPreferences.getString(MainActivity.NEWS, null);
            if (jsonNews == null) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = null;
                if (connMgr != null) {
                    networkInfo = connMgr.getActiveNetworkInfo();
                }
                if (networkInfo == null || !networkInfo.isConnected()) {
                    Toast.makeText(context, "Kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, "Hệ thống đang bảo trì", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                JSONArray arrayNews = new JSONArray(jsonNews);
                listNews.clear();
                for (int i = 0; i < arrayNews.length(); i++) {
                    JSONObject objectNews = arrayNews.getJSONObject(i);
                    listNews.add(new News(objectNews.getString("title"), objectNews.getString("time"), objectNews.getString("link"), R.drawable.image));
                }
                NewsFragment.mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.SHAREDPRENEWS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(MainActivity.NEWS, s);
            editor.apply();
            try {
                JSONArray arrayNews = new JSONArray(s);
                listNews.clear();
                for (int i = 0; i < arrayNews.length(); i++) {
                    JSONObject objectNews = arrayNews.getJSONObject(i);
                    Log.d("khanghoa", objectNews.getString("title"));
                    listNews.add(new News(objectNews.getString("title"), objectNews.getString("time"), objectNews.getString("link"), R.drawable.image));
                }
                NewsFragment.mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
