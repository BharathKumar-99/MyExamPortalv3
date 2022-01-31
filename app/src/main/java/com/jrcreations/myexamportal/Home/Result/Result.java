package com.jrcreations.myexamportal.Home.Result;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.jrcreations.myexamportal.LoginSignup.MainActivity;
import com.jrcreations.myexamportal.R;

public class Result extends AppCompatActivity {
    TextView answered;
    Button back;
    int value,total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        answered=findViewById(R.id.answered);
        back=findViewById(R.id.backtomain);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("answered");
            total = extras.getInt("total");
        }
        answered.setText("You have Answered "+value+"of"+total);
back.setOnClickListener(v->{
    startActivity(new Intent(this, MainActivity.class));
    finishAffinity();
});
    }
}