package com.jrcreations.myexamportal.Home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jrcreations.myexamportal.Home.Items.CurrentAffairs;
import com.jrcreations.myexamportal.Home.Items.PYQ;
import com.jrcreations.myexamportal.Home.Items.Quizes;
import com.jrcreations.myexamportal.Home.Items.TestSeries;
import com.jrcreations.myexamportal.Home.Profile.Profile;
import com.jrcreations.myexamportal.LoginSignup.MainActivity;
import com.jrcreations.myexamportal.Model.Users;
import com.jrcreations.myexamportal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HomeScreen extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar  toolbar;
    ImageView pic;
    TextView name;
    String usrname;
    View view;
    ActionBarDrawerToggle toggle;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        bottomNavigationView=findViewById(R.id.bottom_navigation);

        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.navdrawer);
        drawerLayout=findViewById(R.id.drawer);

        setSupportActionBar(toolbar);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setTitle("");
        getSupportActionBar().setTitle(null);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String id=user.getUid();




        getSupportFragmentManager().beginTransaction().
                replace(R.id.mainconstraint,new TestSeries()).commit();
        bottomNavigationView.setSelectedItemId(R.id.testseries);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                switch (item.getItemId()){
                    case R.id.testseries:
                        fragment =new TestSeries();
                        break;
                    case R.id.quiz:
                        fragment =new Quizes();
                        break;
                    case R.id.currentaffairs:
                        fragment=new CurrentAffairs();
                        break;
                    case R.id.pyq:
                        fragment=new PYQ();
                        break;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainconstraint,fragment).commit();
                return true;
            }
        });


         view=navigationView.getHeaderView(0);

         name=view.findViewById(R.id.navname);
         pic=view.findViewById(R.id.profile_image);
        getname(id,this);

navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                startActivity(new Intent(HomeScreen.this, Profile.class));
                break;
            case R.id.logout1:
                startActivity(new Intent(HomeScreen.this, MainActivity.class));
                FirebaseAuth.getInstance().signOut();
                finishAffinity();
        }
        return true;
    }
});

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
                                usrname=users.getName();
                                name.setText(usrname);
                                if(users.getPic().equals("")){
                                    Glide.with(view).load(R.drawable.baseline_person_blue_800_24dp).into(pic);
                                }else
                                    Glide.with(view).load(users.getPic()).into(pic);


                            }


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

}