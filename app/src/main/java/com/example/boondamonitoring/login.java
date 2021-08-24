package com.example.boondamonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class login extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;
    MainActivity mainActivity = new MainActivity();
    CallbackManager callbackManager;
    private LoginButton loginButton;
    Button overlayGoogleButton;
    String clientID = "781084017847-p8qhp7c8n3862q1teh3ldnc5m79pe28p.apps.googleusercontent.com";

    public static final String SHARED_PREFS = "shared_prefs";

    public static final String NAME_KEY = "name_key";

    public static final String ID_KEY = "pid_key";

    private SignInButton signInButton;
    private GoogleSignInClient googleSignInClient;

    SharedPreferences sharedpreferences;
    String name = "gak null";
    String id = "id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = "gak null";
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.tv_signup).setOnClickListener(this);
        findViewById(R.id.bt_login).setOnClickListener(this);
        editTextEmail = (EditText) findViewById(R.id.et_email);
        editTextPassword = (EditText) findViewById(R.id.et_passwordLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        findViewById(R.id.bt_backlogin).setOnClickListener(this);
        findViewById(R.id.bt_facebooklogin).setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        overlayGoogleButton = findViewById(R.id.bt_googlelogin);


        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        name = sharedpreferences.getString(NAME_KEY, null);
        id = sharedpreferences.getString(ID_KEY, null);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientID)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = findViewById(R.id.signIn_button);
        signInButton.setOnClickListener(this);
        overlayGoogleButton.setOnClickListener(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                    }
                }, 100);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_signup:
                startActivity(new Intent(this, signup.class));
                break;
            case R.id.bt_login:
                login();
                break;
            case R.id.bt_backlogin:
                overridePendingTransition(0, 0);
                finish();
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.bt_facebooklogin:
                LoginManager.getInstance().logIn(this, Arrays.asList("user_photos", "email", "user_birthday", "public_profile"));
                break;
            case R.id.signIn_button:
//                Intent signInIntent = googleSignInClient.getSignInIntent();
//                startActivityForResult(signInIntent, 1);
            case R.id.bt_googlelogin:
                signInButton.performClick();
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 1);
                break;
        }

    }

    private void login() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Please enter email.");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email.");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Please enter password");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    overridePendingTransition(0, 0);
                                }
                            }, 100);

                        } else {
                            Toast.makeText(getApplicationContext(), "Password does not match.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.d("ulololllolololo",task.toString());

//            handleSignInResult(task);
        }

        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("demo", object.toString());

                        try {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(NAME_KEY, object.getString("name"));
                            editor.putString(ID_KEY, object.getString("id"));
                            editor.apply();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "gender,name,id");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
        name = sharedpreferences.getString(NAME_KEY, null);

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(getApplicationContext(), "Sign in Success", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        } catch (ApiException e) {
            Toast.makeText(getApplicationContext(), "Sign in Failed", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }

    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "task Success", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    Toast.makeText(getApplicationContext(), "task Failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser fUser) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();
            String personId = account.getId();

        }
    }
}