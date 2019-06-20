package com.ptit.ptitplus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ptit.ptitplus.asyncTask.AccountAsyncTask;

public class SignInActivity extends AppCompatActivity {

    private EditText editTextUser;
    private EditText editTextPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editTextPass = findViewById(R.id.password);
        editTextUser = findViewById(R.id.username);
    }

    public void skip(View view) {
        saveData();
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHAREDPRESIGNIN, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MainActivity.ISFIRSTTIME, false);
        editor.apply();
    }

    public void signIn(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHAREDPRESIGNIN, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MainActivity.ISFIRSTTIME, false);
        editor.apply();

        String username = editTextUser.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();
        if (username.equalsIgnoreCase("")||password.equalsIgnoreCase("")){
            Toast.makeText(this, "Điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
            return;
        }
        String account[] = {username, password};
        AccountAsyncTask accountAsyncTask = new AccountAsyncTask(SignInActivity.this);
        accountAsyncTask.execute(account);
    }
}
