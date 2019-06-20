package com.ptit.ptitplus.viewfragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ptit.ptitplus.MainActivity;
import com.ptit.ptitplus.R;
import com.ptit.ptitplus.adapter.NewsAdapter;
import com.ptit.ptitplus.asyncTask.NewsAsyncTask;
import com.ptit.ptitplus.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private ArrayList<News> mNewsData;
    public static NewsAdapter mAdapter;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int gridColumnCount = 1;
//                getResources().getInteger(R.integer.grid_column_count);

        // Initialize the RecyclerView.
        mRecyclerView = view.findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), gridColumnCount));

        // Initialize the ArrayList that will contain the data.
        mNewsData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new NewsAdapter(mNewsData, this.getContext());
        mRecyclerView.setAdapter(mAdapter);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MainActivity.SHAREDPRENEWS, Context.MODE_PRIVATE);
        String jsonNews = sharedPreferences.getString(MainActivity.NEWS, null);
        if (jsonNews != null) {
            try {
                JSONArray arrayNews = new JSONArray(jsonNews);
                mNewsData.clear();
                for (int i = 0; i < arrayNews.length(); i++) {
                    JSONObject objectNews = arrayNews.getJSONObject(i);
                    mNewsData.add(new News(objectNews.getString("title"), objectNews.getString("time"), objectNews.getString("link"), R.drawable.image));
                }
                NewsFragment.mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        NewsAsyncTask task = new NewsAsyncTask(getContext(),mNewsData, mAdapter);
        task.execute();

    }

}
