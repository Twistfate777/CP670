package com.example.androidassignments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ChatDatabaseHelper;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ChatWindow";

    SQLiteDatabase database;
    ChatDatabaseHelper tempDatabase;
    private String[] allItems = { ChatDatabaseHelper.COLUMN_ID,
            ChatDatabaseHelper.COLUMN_ITEM };

    private ListView listView;
    private EditText editText;
    private Button btnSend;
    private ArrayList<String> chatMessages;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatMessages = new ArrayList<>();

        tempDatabase = new ChatDatabaseHelper(this);
        database = tempDatabase.getWritableDatabase();

        Cursor cursor = database.query(ChatDatabaseHelper.TABLE_ITEMS,
                allItems,
                ChatDatabaseHelper.COLUMN_ITEM + " not null",
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        // Read database and add to messages
        while(!cursor.isAfterLast())
        {
            Log.i(ACTIVITY_NAME, "New message found in Database. Details below --");
            Log.i(ACTIVITY_NAME, "SQL COLUMN NAME: " + cursor.getColumnName(1));
            String temp = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.COLUMN_ITEM));
            Log.i(ACTIVITY_NAME, "SQL column " + cursor.getColumnName(1) + ": " + temp);
            chatMessages.add(temp);
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
            cursor.moveToNext();
        }
        cursor.close();


        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.edit_Text);
        btnSend = findViewById(R.id.btnSend);

        ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                String temp = editText.getText().toString();
                chatMessages.add(temp);
                editText.getText().clear();
                //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //startActivity(intent);
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()

                // Add to database
                ContentValues cValues = new ContentValues();
                cValues.put(ChatDatabaseHelper.COLUMN_ITEM, temp);

                database.insert(ChatDatabaseHelper.TABLE_ITEMS, "NullPlaceholder", cValues);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


//    @Override
//    public boolean onOptionsItemSelected (MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//        return  super.onOptionsItemSelected(item);
//    }

    private class ChatAdapter extends ArrayAdapter<String>
    {
        public ChatAdapter(Context ctx)
        {
            super(ctx, 0);
        }

        public int getCount()
        {
            return chatMessages.size(); // Return the number of items in array of messages
        }

        public String getItem(int position)
        {
            return chatMessages.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;

            //Log.i(ACTIVITY_NAME, Integer.toString(position));
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = result.findViewById(R.id.message_text);
            message.setText(   /*"Hello"*/ getItem(position) ); // get the string at position
            return result;
        }

    }

}