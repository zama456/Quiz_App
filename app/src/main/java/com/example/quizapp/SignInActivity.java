package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.BreakIterator;

public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private static final String TAG = null;
    TextView tvSignup;
    EditText etEmail, etPassword;
    Button btnSignIn, btnLogGoogle;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog progressDialog;
    private GoogleSignInClient mGoogleSignInClient;
   // private DatabaseReference mRef;
  //  public static final String parentNode = "UserDetails";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tvSignup = findViewById(R.id.tvSignup);
        etEmail = findViewById(R.id.etEmailSignIn);
        btnLogGoogle = findViewById(R.id.btnLogGoogle);
        etPassword = findViewById(R.id.etPasswordSignIn);
        progressDialog = new ProgressDialog(this);
        btnSignIn = findViewById(R.id.btnSignIn);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        //mRef = FirebaseDatabase.getInstance().getReference().child(parentNode);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
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

        btnLogGoogle.setOnClickListener(v -> {
            SignIn();
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                perforSignIn();

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

                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
            }

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "e.getMessage()", Toast.LENGTH_SHORT).show();
        }
    }


    private void perforSignIn() {

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("please enter your Email!");
            etEmail.requestFocus();
        } else if (password.isEmpty() || password.length() < 6) {
            etPassword.setError("please enter correct password");
            etPassword.setError("password must not be <6 !");
            etPassword.requestFocus();
        } else {
            progressDialog.setMessage("please wait while Signing In....");
            progressDialog.setTitle("SignIn");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this, "Error SignIn Failed !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignInActivity.this, "SignIn Successful" + task.getException(), Toast.LENGTH_SHORT).show();
                        //mRef.child("username").setValue(username);
                        //mRef.child("email").setValue(email);
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();
                    }
                }

            });


        }
    }
}

