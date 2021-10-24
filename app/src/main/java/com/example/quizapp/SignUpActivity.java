package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity<SingUpActivity> extends AppCompatActivity {

    TextView tvAlreadyaccount;
    EditText etUsername,etEmail,etPassword;
    Button btnSignup;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        tvAlreadyaccount = findViewById(R.id.tvAlreadyaccount);
        btnSignup = findViewById(R.id.btnSignup);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        ProgressDialog progressDialog = new ProgressDialog(this);
        etUsername = findViewById(R.id.etUsername);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        tvAlreadyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                perforAuth();
            }

            private void perforAuth() {

                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (email.isEmpty()) {
                    etEmail.setError("please enter your Email");
                    etEmail.requestFocus();
                } else if (password.isEmpty() || password.length() < 6) {
                    etPassword.setError("please enter your password");
                    etPassword.requestFocus();
                } else if (!email.isEmpty() && !password.isEmpty())
                {
                    progressDialog.setMessage("please wait while signing up....");
                    progressDialog.setTitle("Signup");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    Toast.makeText(SignUpActivity.this, "This email is already exist", Toast.LENGTH_SHORT).show();

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "SignUp Failed !", Toast.LENGTH_SHORT).show();
                            } else {
                                if(task.isSuccessful())
                                    Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                               //Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            }
                        }

                    });

                }
            }
        });
    }
}