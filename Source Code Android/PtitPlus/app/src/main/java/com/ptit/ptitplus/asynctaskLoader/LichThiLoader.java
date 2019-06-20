package com.ptit.ptitplus.asynctaskLoader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.ptit.ptitplus.api.APILichThi;
import com.ptit.ptitplus.api.APIMark;

public class LichThiLoader extends AsyncTaskLoader {
    private String user;
    private String pass;
    public LichThiLoader(@NonNull Context context, String user, String pass) {
        super(context);
        Log.d("lichthi", "khoi tao");
        this.user = user;
        this.pass = pass;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d("lichthi", "start loading");
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        Log.d("lichthi", "load in background");
        return APILichThi.getLichThi(user, pass);
    }
}
