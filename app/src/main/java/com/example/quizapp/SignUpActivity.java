package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
    EditText etUsername, etEmail, etPassword;
    Button btnSignup;
    AlertDialog alertDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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

                if (username.isEmpty()) {
                    etUsername.setError("Username must not be empty!");
                }
                if (email.isEmpty()) {
                    etEmail.setError("please enter your Email!");
                    etEmail.requestFocus();
                } else if (password.isEmpty() || password.length() < 6) {
                    etPassword.setError("password must not <6!");
                    etPassword.requestFocus();
                } else if (!email.isEmpty() && !password.isEmpty()) {
                    progressDialog.setMessage("please wait while signing up....");
                    progressDialog.setTitle("Signup");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();


                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "Error SignUp Failed !", Toast.LENGTH_SHORT).show();
                            } else {
                                if (task.isSuccessful())
                                    Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                                finish();
                            }
                        }

                    });

                }
            }
        });


    }


    @Override
    public void onBackPressed() {
            AlertDialog.Builder alretdialog = new AlertDialog.Builder(this);
            alretdialog.setTitle("Exit!");
            alretdialog.setIcon(R.drawable.ic_baseline_warning_24);
            alretdialog.setCancelable(false);
            alretdialog.setMessage("Are sure you want to exit ??");
            alretdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });
            alretdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alretdialog.create();
            alretdialog.show();

    }
}


