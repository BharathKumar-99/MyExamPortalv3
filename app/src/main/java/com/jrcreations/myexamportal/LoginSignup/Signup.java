package com.jrcreations.myexamportal.LoginSignup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jrcreations.myexamportal.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class Signup extends Fragment {
    TextView login;
    EditText date;
    EditText email,password,name,phone,confirm_password;
    final Calendar myCalendar= Calendar.getInstance();
    Button signup;
    String emailstr,passswordstr,namestr,phonestr,confirmstr;
    private FirebaseAuth mAuth;
    boolean emailvalid,passvalid;
    View view;



    public Signup() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Signup newInstance(String param1, String param2) {
        Signup fragment = new Signup();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_signup, container, false);

        mAuth = FirebaseAuth.getInstance();

        mAuth = FirebaseAuth.getInstance();

        login=view.findViewById(R.id.loginret);
        email=view.findViewById(R.id.signupemail);
        password=view.findViewById(R.id.signuppassword);
        name=view.findViewById(R.id.signupname);
        phone=view.findViewById(R.id.signupphone);
        date=view.findViewById(R.id.date);
        confirm_password=view.findViewById(R.id.signupconfirmpassword);
        signup=view.findViewById(R.id.signup);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());
        date.setText(currentDateandTime);



        date.setOnClickListener(v->{
            new DatePickerDialog(getContext(),date3,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH)
                    ,myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });




        signup.setOnClickListener(v->{


            emailstr=email.getText().toString();
            passswordstr=password.getText().toString();
            namestr=name.getText().toString();
            phonestr=phone.getText().toString();
            confirmstr= confirm_password.getText().toString();

            emailvalid = validator(emailstr);
            passvalid=passwordmatch(passswordstr,confirmstr);

            if(emailvalid && passvalid){
                //save credential
                createAccount(emailstr,passswordstr);

            }

        });







        return view;
    }


    private void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "createUserWithEmail:success");
                        Toast.makeText(getContext(), "Successfully Created", Toast.LENGTH_SHORT).show();

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        Databaseupload db=new Databaseupload();
                        String dob=date.getText().toString();
                        assert user != null;
                        String id=user.getUid();
                        db.postDataUsingVolley(namestr,emailstr,getContext(),id,phonestr,dob);
                        requireActivity().onBackPressed();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("TAG", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(getContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }

    DatePickerDialog.OnDateSetListener date3 = (view, year, month, day) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH,month);
        myCalendar.set(Calendar.DAY_OF_MONTH,day);
        updateLabel();
    };
    private void updateLabel(){
        String myFormat="MM-dd-yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        date.setText(dateFormat.format(myCalendar.getTime()));
    }


    protected boolean validator(String emailtest){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailtest.matches(emailPattern))
        {
            return true;
        }
        else
        {
            email.setError("Email Invalid");
            return false;
        }

    }
    protected boolean passwordmatch(String pass,String conpass){

        if(pass.equals(conpass) && pass.length()>=6){
            return true;
        }
        else{
            password.setError("Password Mismatch");
            return false;
        }

    }
}