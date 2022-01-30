package com.jrcreations.myexamportal.LoginSignup;


import android.content.Context;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Databaseupload {

    public void postDataUsingVolley(String name, String email, Context context,
                                    String id,String phone,String dob) {
        // url to post our data
        String url = "https://myexamportals.com/Php/signup.php";


        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    // inside on response method we are
                    // hiding our progress bar
                    // and setting data to edit text as empty


                    // on below line we are displaying a success toast message.

                    Log.d("TAG", response.toString());


                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Log.d("TAG", "onErrorResponse: "+ error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("name", name);
                params.put("email", email);
                params.put("IDNUM", id);
                params.put("phone", phone);
                params.put("dob", dob);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }


}