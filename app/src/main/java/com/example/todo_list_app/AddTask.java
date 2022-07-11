package com.example.todo_list_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import androidx.appcompat.app.AppCompatActivity;

public class AddTask extends AppCompatActivity {
    private EditText editText;
    private EditText editText2;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        editText = (EditText) findViewById(R.id.taskname);
        editText2 = (EditText) findViewById(R.id.taskoutput);
        button = (Button) findViewById(R.id.savebtn);
        //Edittext + button - get valua from edit text, save and send to server
        //Execute Save onClick Save saveTask() and move back to home screen;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = editText.getText().toString();

                if (TextUtils.isEmpty(taskName)) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(AddTask.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.
                    saveTask(taskName);
                    Intent intent = new Intent (AddTask.this,  HomeScreen.class);
                    startActivity(intent);
                }
            }

//

        });
    }

    public void saveTask(String taskValue) {

        String url = "http://192.168.178.77/todo-list-api/create-task.php";

        RequestQueue queue = Volley.newRequestQueue(AddTask.this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            // on below line we are displaying a success toast message.
                            Toast.makeText(AddTask.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // and setting data to edit text as empty
                        editText.setText("");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddTask.this, "Fail to get course" + error, Toast.LENGTH_SHORT).show();
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