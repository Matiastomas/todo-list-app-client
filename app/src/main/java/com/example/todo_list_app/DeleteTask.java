package com.example.todo_list_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeleteTask extends AppCompatActivity {
   private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_task);
         editText = findViewById(R.id.updatetask);
        Button button = findViewById(R.id.deleteBtn);
        Intent intent = getIntent();
        String message = intent.getStringExtra("taskValue");
        editText.setText(message);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String getEditTextValue = editText.getText().toString();
              deleteTask(getEditTextValue);
               Intent intent = new Intent (DeleteTask.this,  HomeScreen.class);
               startActivity(intent);
           }
       });


    }

    public void deleteTask(String taskValue) {

        String url = "http://192.168.178.77/todo-list-api/delete-task.php";
        RequestQueue queue = Volley.newRequestQueue(DeleteTask.this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            // on below line we are displaying a success toast message.
                            Toast.makeText(DeleteTask.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // and setting data to edit text as empty
                        editText.setText("");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeleteTask.this, "Fail to delete task" + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("taskname", taskValue);
                return map;
            }
        };

        queue.add(stringRequest);
    }

}