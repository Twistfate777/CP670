package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    public static final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Click the button and jump to List_Items
    public void onJumpClicked(View v)
    {
        Log.i(TAG,"onJumpClicked");
        Intent intent = new Intent(MainActivity.this,ListItemsActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    public void onChatClicked(View v) {
        Log.i(TAG,"User clicked Start Chat");
        Intent intent = new Intent(MainActivity.this, ChatWindow.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    //Receive the message from previous page
    protected void onActivityResult(int RequestCode, int ResultCode, Intent data) {
        Log.i(TAG,"onActivityResult");
        super.onActivityResult(ResultCode, ResultCode, data);
        if (RequestCode == REQUEST_CODE && ResultCode == RESULT_OK) {
            Log.i(TAG,"You are back to Main Page!");
            String message = data.getStringExtra("Response");
            CharSequence text = message;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this,text,duration);
            toast.show();
        } else {
            Log.i(TAG,"OnReturnMain");
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