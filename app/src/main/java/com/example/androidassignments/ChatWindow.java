package com.example.androidassignments;

import android.content.Context;
import android.os.Bundle;
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

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    private ListView listView;
    private EditText editText;
    private Button btnSend;
    private ArrayList<String> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.edit_Text);
        btnSend = findViewById(R.id.btnSend);

        chatMessages = new ArrayList<>();

        ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString().trim();

                if(!message.isEmpty()) {
                    chatMessages.add(message);

                    messageAdapter.notifyDataSetChanged();

                    editText.setText("");
                }
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

    private class ChatAdapter extends ArrayAdapter<String> {

        private LayoutInflater inflater;

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
            inflater = ChatWindow.this.getLayoutInflater();
        }

        @Override
        public int getCount() {
            return chatMessages.size();
        }

        @Override
        public String getItem(int position) {
            return chatMessages.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            inflater = ChatWindow.this.getLayoutInflater();

            String message = getItem(position);
            View result;

            if (position % 2 == 0) {
                // If the position is even, inflate the incoming message layout
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                // If the position is odd, inflate the outgoing message layout
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            // Get the TextView which holds the string message
            TextView messageTextView = (TextView) result.findViewById(R.id.message_text);
            messageTextView.setText(message); // Set the text for the TextView

            return result;
        }
    }

}