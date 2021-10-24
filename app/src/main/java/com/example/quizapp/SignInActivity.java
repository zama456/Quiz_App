package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.BreakIterator;

public class SignInActivity extends AppCompatActivity {
    
    TextView tvSignup;
    EditText etEmail,etPassword;
    Button btnSignIn;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tvSignup = findViewById(R.id.tvSignup);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            }

        });

    }

}
