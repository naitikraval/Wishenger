package com.wishenger.android.wishengerr.RegisterAndLogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wishenger.android.wishengerr.MainActivity;
import com.wishenger.android.wishengerr.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import maes.tech.intentanim.CustomIntent;

public class Login extends AppCompatActivity {

    private EditText editTextMobile;
//        private Button googleSignInButton;
//    SignInButton googleSignInButton;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleSignInClient;
    private  static final int RC_SIGN_IN = 9001;
    private ProgressDialog loadingBar;

    GoogleSignInButton googleSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        editTextMobile = findViewById(R.id.editTextMobile);
//        googleSignInButton = (Button) findViewById(R.id.google_signin_button);

//        googleSignInButton = (SignInButton) findViewById(R.id.google_signin_button);
        googleSignInButton = (GoogleSignInButton) findViewById(R.id.google_signin_button);
//        googleSignInButton = (Button) findViewById(R.id.g_btn);
        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//
//        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String mobile = editTextMobile.getText().toString().trim();
//
//                if(mobile.isEmpty() || mobile.length() < 10){
//                    editTextMobile.setError("Enter a valid mobile");
//                    editTextMobile.requestFocus();
//                    return;
//                }
//
//                Intent intent = new Intent(Login.this, VerifyOTP.class);
//                intent.putExtra("mobile", mobile);
//                startActivity(intent);
//                finish();
//            }
//        });



        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
                    {
                        Toast.makeText(Login.this,"Connection to google sign in is failed",Toast.LENGTH_SHORT).show();

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }

        });

        if(currentFirebaseUser != null)
        {
            currentFirebaseUser.getUid();
//            Toast.makeText(this, "User Id:" + currentFirebaseUser, Toast.LENGTH_SHORT).show();

            Intent i = new Intent(Login.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }



    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN)
        {
            loadingBar.setTitle("Google Sign In");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess())
            {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
//                Toast.makeText(Login.this,"Please wait, while we are getting your auth result...",Toast.LENGTH_SHORT).show();

            }

            else
            {
                Toast.makeText(Login.this,"Failed, Please Try again!",Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }

        }


    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information

                            SendUserToMainActivity();
//                            progressDialog.dismiss();

                        } else
                        {
                            // If sign in fails, display a message to the user.

                            String msg = task.getException().toString();
                            SendUserToLoginActivity();
                            Toast.makeText(Login.this,"Not Authenticated :"+msg,Toast.LENGTH_SHORT).show();

//                            progressDialog.dismiss();
                        }

                        // ...
                    }
                });
    }

    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(Login.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        CustomIntent.customType(Login.this,"left-to-right");
        finish();
    }

    private void SendUserToLoginActivity()
    {
        Intent mainIntent = new Intent(Login.this, Login.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        CustomIntent.customType(Login.this,"left-to-right");
        finish();
    }


}


