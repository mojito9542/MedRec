package com.illuminati.www.medrec;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText memail, mpassword;
    Button  login;
    TextView register;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(memail.getText().toString(), mpassword.getText().toString());

            }
        });
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Signup.class));
            }
        });



    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Successful",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            memail.setText("");
                            mpassword.setText("");
                            Log.d(TAG, "signInWithEmail:success");

                        } else {

                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = memail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            memail.setError("Required.");
            valid = false;
        } else if (!email.contains("@") && !email.contains(".")) {
            valid = false;
            memail.setError("enter valid email id");
        }

        String password = mpassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mpassword.setError("Required.");
            valid = false;
        }
        else if(password.length()<6){
            mpassword.setError("atleast 6 character");
            valid= false;
        }
                else {
            mpassword.setError(null);
        }

        return valid;
    }
}


















