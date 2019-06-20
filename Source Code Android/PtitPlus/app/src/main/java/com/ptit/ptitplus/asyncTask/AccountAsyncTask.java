package com.ptit.ptitplus.asyncTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ptit.ptitplus.MainActivity;
import com.ptit.ptitplus.SignInActivity;
import com.ptit.ptitplus.api.APIAccount;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;
    private String user;
    private String pass;

    public AccountAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        user = strings[0];
        pass = strings[1];
        return APIAccount.getAccount(strings[0], strings[1]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            String name = jsonObject.getString("ten");
            Log.d("account", name);
            if (name.equalsIgnoreCase("nobody")) {
                Toast.makeText(context, "Sai thông tin " + name, Toast.LENGTH_LONG).show();
                return;
            }
            String ngaySinh = jsonObject.getString("ngaySinh");
            String lop = jsonObject.getString("lop");
            String khoa = jsonObject.getString("khoa");
            SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.SHAREDPRESIGNIN, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(MainActivity.USERNAME, user);
            editor.putString(MainActivity.PASSWORD, pass);
            editor.putString(MainActivity.NAME, name);
            editor.putString(MainActivity.NGAYSINH, ngaySinh);
            editor.putString(MainActivity.LOP, lop);
            editor.putString(MainActivity.KHOA, khoa);
            editor.apply();
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        } catch (JSONException e) {
            Toast.makeText(context, "Hệ thống đang quá tải, thử lại sau!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
