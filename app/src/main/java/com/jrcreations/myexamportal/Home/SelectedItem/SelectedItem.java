package com.jrcreations.myexamportal.Home.SelectedItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jrcreations.myexamportal.Home.RecycleView.RecycleMock;
import com.jrcreations.myexamportal.Home.RecycleView.SelectedRecycleView;
import com.jrcreations.myexamportal.Model.MockTestModel;
import com.jrcreations.myexamportal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectedItem extends AppCompatActivity {
String value;
Toolbar toolbar;
    private ProgressDialog pDialog;
    private List<MockTestModel> mockTestModels;
    SelectedRecycleView adapter;
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("slelectedui");
            //The key argument here must match that used in the other activity
        }
        recyclerView=findViewById(R.id.selecteduiview);
        toolbar=findViewById(R.id.toolbarsi);
        toolbar.setTitle(value);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        mockTestModels=new ArrayList<>();
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading");
        pDialog.show();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new SelectedRecycleView(this,mockTestModels);
        recyclerView.setAdapter(adapter);
        getname();

    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    public void getname() {


        //getting the progressbar
        String url = "https://myexamportals.com/Php/getSelection.php";
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray tutorialsArray = new JSONArray(response);


                            for (int i = 0; i < tutorialsArray.length(); i++) {
//                                getting the json object of the particular index inside the array
                                JSONObject tutorialsObject = tutorialsArray.getJSONObject(i);
                                MockTestModel users = new MockTestModel();
                                users.setName(tutorialsObject.getString("name"));
                                users.setTime(tutorialsObject.getInt("time"));
                                users.setMarks(tutorialsObject.getInt("marks"));
                                users.setQuestions(tutorialsObject.getInt("questions"));
                                //creating a tutorial object and giving them the values from json object
                                mockTestModels.add(users);
                                Log.d("TAG", "onResponse: "+users.getMarks());

                            }
                            hidePDialog();
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> {
                    //displaying the error in toast if occur
                    Log.d("TAG", "onErrorResponse: " + error);
                    error.printStackTrace();
                }) {
            @Override
            public Map getParams() {
                Map params = new HashMap();

                params.put("name", "Mock_Test");
                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}