package com.jrcreations.myexamportal.Home.Quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jrcreations.myexamportal.Home.Result.Result;
import com.jrcreations.myexamportal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz extends AppCompatActivity {

    private ProgressDialog pDialog;
    List<String> correctans;
    List<String> questions1;
    List<String> otp1;
    List<String> otp2;
    List<String> otp3;
    List<String> otp4;
    int i=0;
    int correct=0,wrong=0;
    TextView time, questionleft, question;
    Button c1, c2, c3, c4;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        correctans=new ArrayList<>();
        questions1=new ArrayList<>();
        otp1=new ArrayList<>();
        otp2=new ArrayList<>();
        otp3=new ArrayList<>();
        otp4=new ArrayList<>();
        time = findViewById(R.id.qtime);
        toolbar=findViewById(R.id.toolbar3);
        toolbar.setTitle("Quizzes");
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading");
        pDialog.show();
        questionleft = findViewById(R.id.qql);
        question = findViewById(R.id.quizquestion);
        c1 = findViewById(R.id.otp1);
        c2 = findViewById(R.id.otp2);
        c3 = findViewById(R.id.otp3);
        c4 = findViewById(R.id.otp4);
timer();
        getQuestions();





        c1.setOnClickListener(v->{
            if(c1.getText().equals(correctans.get(i))){
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                i++;
                settext();
                correct++;
                timer();
            }else {
                Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
                i++;
                settext();
                wrong++;
                timer();
            }
        });
        c2.setOnClickListener(v->{
            if(c2.getText().equals(correctans.get(i))){
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                i++;
                settext();
                correct++;
                timer();
            }
            else {
                Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
                i++;
                settext();
                wrong++;
                timer();

            }
        });
        c3.setOnClickListener(v->{
            if(c3.getText().equals(correctans.get(i))){
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                i++;
                settext();
                correct++;
                timer();
            }
            else {
                Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
                i++;
                settext();
                wrong++;
                timer();
            }
        });
        c4.setOnClickListener(v->{
            if(c4.getText().equals(correctans.get(i))){
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                i++;
                settext();
                correct++;
                timer();
            }
            else {
                Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
                i++;
                settext();
                wrong++;
                timer();
            }
        });






    }

    public void timer(){
        long millisInFuture = 30000; //30 seconds
        long countDownInterval = 1000;
        new CountDownTimer(millisInFuture,countDownInterval){
            public void onTick(long millisUntilFinished){

                    time.setText(String.valueOf (millisUntilFinished / 1000));

            }
            public void onFinish(){


            }
        }.start();


    }

    public void getQuestions(){


        //getting the progressbar
        String url = "https://myexamportals.com/Php/getSelection.php";
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {

                    try {

                        JSONArray tutorialsArray = new JSONArray(response);


                        for (int i = 0; i < tutorialsArray.length(); i++) {
                            //getting the json object of the particular index inside the array
                            JSONObject tutorialsObject = tutorialsArray.getJSONObject(i);
                            Log.d("TAG", "getQuestions: "+response.toString());

                            otp1.add(tutorialsObject.getString("otp1"));
                            otp2.add(tutorialsObject.getString("otp2"));
                            otp3.add(tutorialsObject.getString("otp3"));
                            otp4.add(tutorialsObject.getString("otp4"));
                            questions1.add(tutorialsObject.getString("questions"));
                            correctans.add(tutorialsObject.getString("correctans"));
                            //creating a tutorial object and giving them the values from json objec
                        }
                        settext();
                        hidePDialog();
//                    adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("TAG", "getQuestions: ");
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
                params.put("name", "Mock_Test_1_Questions");
                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
    public void settext(){
        questionleft.setText("Total Questions:" + questions1.size());

        if(questions1.size() <= i){

            Intent intent = new Intent(this, Result.class);
            intent.putExtra("answered",correct);
            intent.putExtra("total",questions1.size());
            startActivity(intent);
            finishAffinity();
            finish();
        }else {
            question.setText(questions1.get(i));
            c1.setText(otp1.get(i));
            c2.setText(otp2.get(i));
            c3.setText(otp3.get(i));
            c4.setText(otp4.get(i));
        }
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}