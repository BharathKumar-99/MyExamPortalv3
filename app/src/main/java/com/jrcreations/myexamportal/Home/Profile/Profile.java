package com.jrcreations.myexamportal.Home.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jrcreations.myexamportal.Model.Users;
import com.jrcreations.myexamportal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
private TextView name,email,phone,attempt,pname;
ImageView pic;
    private ProgressDialog pDialog;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar=findViewById(R.id.toolbar4);
        toolbar.setTitle("Profile");

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        name=findViewById(R.id.pname);
        email=findViewById(R.id.pemail);
        phone=findViewById(R.id.pphone);
        attempt=findViewById(R.id.attempt);
        pic=findViewById(R.id.profile_image);
        pname=findViewById(R.id.namep);
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading");
        pDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String id=user.getUid();

        getname(id,this);
    }
    public void getname(String id, Context context) {


        //getting the progressbar
        String url = "https://myexamportals.com/Php/getuser.php";
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray tutorialsArray = new JSONArray(response);


                            for (int i = 0; i < tutorialsArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject tutorialsObject = tutorialsArray.getJSONObject(i);
                                Users users = new Users();
                                users.setName(tutorialsObject.getString("name"));
                                users.setDob(tutorialsObject.getString("dob"));
                                users.setEmail(tutorialsObject.getString("email"));
                                users.setPhone(tutorialsObject.getString("phone"));
                                users.setPic(tutorialsObject.getString("pic"));
                                //creating a tutorial object and giving them the values from json object
                                Log.d("TAG", "onResponse: " + users.getName());
                                Log.d("TAG", "onResponse: "+response.toString());

                                name.setText(users.getName());
                                pname.setText(users.getName());
                                email.setText(users.getEmail());
                                phone.setText(users.getPhone());
                                attempt.setText(users.getDob());

                                if(users.getPic().equals("")){
                                    Glide.with(Profile.this).load(R.drawable.baseline_person_blue_800_24dp).into(pic);
                                }else
                                    Glide.with(Profile.this).load(users.getPic()).into(pic);

                            }
                            hidePDialog();


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

                params.put("id", id);

                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

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