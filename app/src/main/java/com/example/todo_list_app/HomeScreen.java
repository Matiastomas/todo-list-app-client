package com.example.todo_list_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeScreen extends AppCompatActivity {
    private ListView mListView;
    private Button button;
    private TextView output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        mListView = (ListView) findViewById(R.id.tasklist);
        button = (Button) findViewById(R.id.addtask);

        output = (TextView) findViewById(R.id.output);
        displayTask();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (HomeScreen.this, AddTask.class);
                startActivity(intent);
            }
        });
    }
    //Request tasks from api
    public void displayTask(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.178.77/todo-list-api/read-task.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONArray  arr = new JSONArray(response);

                            List<String> list = new ArrayList<String>();
                            for (int i=0; i<arr.length(); i++) {
                                list.add( arr.getString(i) );
                            }
                            ArrayAdapter<String> taskItems = new ArrayAdapter<String>(HomeScreen.this, android.R.layout.simple_list_item_1, list);
                              mListView.setAdapter(taskItems);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //output.setText("Response is: " + response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                output.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}