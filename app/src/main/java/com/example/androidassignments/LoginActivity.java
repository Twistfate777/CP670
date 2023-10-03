package com.example.androidassignments;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SharedMemory;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadUserData();
    }

    public void onSaveClicked(View v) {
        saveUserData();
        SharedPreferences pref = getSharedPreferences("Email",MODE_PRIVATE);
        String LoginName = pref.getString("Email","");
        Toast.makeText(getApplicationContext(),
                "Welcome!"+LoginName,
                Toast.LENGTH_SHORT).show();
        Intent mIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(mIntent);
    }

    private void saveUserData() {
        String preference_file_name = "Email";
        SharedPreferences mPrefs = getSharedPreferences(preference_file_name,MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        String newEmail = (String) ((EditText)findViewById(R.id.LoginText)).getText().toString();
        mEditor.putString("Email",newEmail);
        mEditor.apply();
    }

    private void loadUserData() {
        SharedPreferences myPrefs = LoginActivity.this.getSharedPreferences(
                "Email", Context.MODE_PRIVATE);
        String UserName = myPrefs.getString("Email", "");
        if (UserName == null) {
            SharedPreferences.Editor mEditor = myPrefs.edit();
            String defaultValue = "email@domain.com";
            mEditor.putString("Email",defaultValue);
            mEditor.apply();
        } else {
            SharedPreferences.Editor mEditor = myPrefs.edit();
            mEditor.putString("Email",UserName);
            mEditor.apply();
        }
    }

    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
    }

    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
    }

    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }

}