package com.example.androidassignments;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {

    private static final String TAG = "ListItemActivity";

    static final int  REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
    }

    //Activity after clicking the ImageButton
    public void onImageButtonCLick(View v)
    {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        }
        Intent TakePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(TakePhotoIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Log.i(TAG,"image button error");
        }
    }

    //Set photo as button image
    protected void onActivityResult(int RequestCode, int ResultCode, Intent data) {
        super.onActivityResult(RequestCode, ResultCode, data);
        if (RequestCode == REQUEST_IMAGE_CAPTURE && ResultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            ImageButton imageButton = findViewById(R.id.imageButton);
            imageButton.setImageBitmap(image);
        }
    }

    public void setOnCheckedChanged(View v) {
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch1 = findViewById(R.id.switch1);
        boolean status = switch1.isChecked();
        CharSequence text_on = "Switch is On";// "Switch is Off"
        CharSequence text_off = "Switch is Off";// "Switch is Off"
        int duration_short = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
        int duration_long = Toast.LENGTH_LONG; //= Toast.LENGTH_LONG if Off
        Toast toast_on = Toast.makeText(this , text_on, duration_short); //this is the ListActivity
        Toast toast_off = Toast.makeText(this , text_off, duration_long);
        if (status){
            toast_on.show();
        } else {
            toast_off.show();
        }
    }

    public void OnCheckChanged(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message) //Add a dialog message to strings.xml
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("Response", "You are back to Main Page!");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                })
                .show();
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