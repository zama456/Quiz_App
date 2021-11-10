package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AccountManagerFuture;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private static final String TAG = null;
    public static final String parentNode = "UserDetails";
    TextView tvAlreadyaccount, tvSignUpNo;
    EditText etUsername, etEmail, etPassword;
    Button btnSignup, btnGoogle;
    AlertDialog alertDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tvAlreadyaccount = findViewById(R.id.tvAlreadyaccount);
        btnSignup = findViewById(R.id.btnSignup);
        btnGoogle = findViewById(R.id.btnGoogle);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        tvSignUpNo = findViewById(R.id.tvSignUpNo);
        ProgressDialog progressDialog = new ProgressDialog(this);
        etUsername = findViewById(R.id.etUsername);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mRef = FirebaseDatabase.getInstance().getReference().child(parentNode);


        tvAlreadyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tvSignUpNo.setOnClickListener(view -> {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
        });


        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        // Set the dimensions of the sign-in button.

        btnGoogle.setOnClickListener(v -> {
            SignIn();
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
                                Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                mRef.child("username").setValue(username);
                                mRef.child("email").setValue(email);
                                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                                finish();
                            }
                        }

                    });

                }
            }
        });

    }


    private void SignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                finish();
            }

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "e.getMessage()", Toast.LENGTH_SHORT).show();
        }
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

