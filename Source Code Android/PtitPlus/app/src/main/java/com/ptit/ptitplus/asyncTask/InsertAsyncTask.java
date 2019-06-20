package com.ptit.ptitplus.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ptit.ptitplus.api.APIInsert;

public class InsertAsyncTask extends AsyncTask<String, Void, String> {

    Context context;

    public InsertAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        return APIInsert.sendCMT(strings[0], strings[1], strings[2], strings[3] );
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.equalsIgnoreCase("done")){
            Toast.makeText(context, "Thêm nhận xét thành công!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "Xảy ra lỗi!", Toast.LENGTH_LONG).show();
        }
    }
}
