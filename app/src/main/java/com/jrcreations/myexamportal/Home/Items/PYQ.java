package com.jrcreations.myexamportal.Home.Items;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jrcreations.myexamportal.Home.RecycleView.RecycleMock;
import com.jrcreations.myexamportal.Home.RecycleView.SelectedRecycle;
import com.jrcreations.myexamportal.Model.MockTestModel;
import com.jrcreations.myexamportal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PYQ extends Fragment {

    TextView name;
    private List<MockTestModel> mockTestModels;
    RecyclerView recyclerView;
    String value;
    private ProgressDialog pDialog;
    SelectedRecycle adapter;

    public PYQ() {
        // Required empty public constructor
    }

    public static PYQ newInstance(String param1, String param2) {
        PYQ fragment = new PYQ();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_p_y_q, container, false);
        mockTestModels=new ArrayList<>();

        recyclerView=view.findViewById(R.id.pyqrec);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter=new SelectedRecycle(getContext(),mockTestModels);
        recyclerView.setAdapter(adapter);
        pDialog = new ProgressDialog(getContext());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading");
        pDialog.show();
        getname();
        return view;

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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}