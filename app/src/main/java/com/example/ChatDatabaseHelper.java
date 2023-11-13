package com.example;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "Messages.db";
    private static final int VERSION_NUM = 1;

    public static final String COLUMN_ID = "KEY_ID";
    public static final String COLUMN_ITEM = "KEY_MESSAGE";

    // Table name and column names
    public static final String TABLE_ITEMS = "items";

    // SQL statement to create a new table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_ITEMS + " ( " +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ITEM + " TEXT)";

    // Constructor
    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    // onCreate method is called to create the database if it doesn't exist
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        db.execSQL(TABLE_CREATE);
    }

    // onUpgrade method is called when the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ChatDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }
}
