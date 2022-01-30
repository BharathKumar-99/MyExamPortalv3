package com.jrcreations.myexamportal.LoginSignup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jrcreations.myexamportal.Home.HomeScreen;
import com.jrcreations.myexamportal.R;





public class Login extends Fragment {
    Button login;
    EditText email,password;
    View view;
    private FirebaseAuth mAuth;
    boolean emailvalid;
    public Login() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_login, container, false);
        email=view.findViewById(R.id.loginemail);
        password=view.findViewById(R.id.loginpassword);
        login=view.findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(v->{
            String emailstr, pass;

            emailstr =email.getText().toString();
            pass = password.getText().toString();
            emailvalid = validator(emailstr);
            if(pass.length()<=5){
                password.setError("Password is too short");
            }
            if (emailvalid && pass.length() <= 6) {
                signIn(emailstr,pass);
                email.setText("");
                password.setText("");
            }
        });
        return view;
    }

    protected boolean validator(String emailtest) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailtest.matches(emailPattern)) {
            Log.d("Tag1","valid");
            return true;

        } else {
            email.setError("Email Invalid");
            return false;
        }

    }


    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) requireContext(), (OnCompleteListener<AuthResult>) task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success");
                        startActivity(new Intent(getContext(), HomeScreen.class));
                        requireActivity().finish();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("TAG1", "signInWithEmail:failure", task.getException());
                        Toast.makeText(getContext(), "Invalid Email or Password", Toast.LENGTH_SHORT).show();

                    }
                });


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(getContext(), HomeScreen.class));
            requireActivity().finish();
        }
    }




}