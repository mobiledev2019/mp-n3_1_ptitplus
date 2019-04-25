package com.ptit.ptitplus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class MarkLoader extends AsyncTaskLoader<String> {

    private String msv;
    public MarkLoader(@NonNull Context context, String msv) {
        super(context);
        this.msv = msv;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return APIMark.getMark(msv);
    }
}
