package com.example.todo_list_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class DeleteTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_task);
        EditText editText = findViewById(R.id.updatetask);
        Intent intent = getIntent();
        String message = intent.getStringExtra("taskValue");
        editText.setText(message);

    }
}