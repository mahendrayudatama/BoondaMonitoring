package com.example.boondamonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.boondamonitoring.model.User;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity implements View.OnClickListener {

    EditText editTextFirstName, editTextLastName, editTextPhoneNumber, editTextYourEmail, editTextPassword, editTextConfirmPassword;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextFirstName = (EditText) findViewById(R.id.et_firstname);
        editTextLastName = (EditText) findViewById(R.id.et_lastname);
        editTextPhoneNumber = (EditText) findViewById(R.id.et_phone);
        editTextYourEmail = (EditText) findViewById(R.id.et_emailsignin);
        editTextPassword = (EditText) findViewById(R.id.et_passwordsignin);
        editTextConfirmPassword = (EditText) findViewById(R.id.et_confirmPasswordsignin);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.bt_signup).setOnClickListener(this);
        findViewById(R.id.bt_backsignup).setOnClickListener(this);
        findViewById(R.id.tv_login).setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBarSignup);
    }

    private void registerUser() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String email = editTextYourEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (firstName.isEmpty()) {
            editTextFirstName.setError("Name is required!");
            editTextFirstName.requestFocus();
            return;
        }
        if (phoneNumber.isEmpty()) {
            editTextPhoneNumber.setError("Phone number is required!");
            editTextPhoneNumber.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editTextYourEmail.setError("Email is required!");
            editTextYourEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextYourEmail.setError("Please enter a valid email!");
            editTextYourEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if (!editTextConfirmPassword.getText().toString().equals(editTextPassword.getText().toString())) {
            editTextConfirmPassword.setError("Password does not match!");
            editTextConfirmPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Password minimum length should be 6 character!");
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_SHORT).show();


                    String newID = email.replace("@","").replace(".","");
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("user").child(newID);
                    User pengguna = new User(firstName,lastName,phoneNumber,email);
                    current_user_db.setValue(pengguna);
                    Log.d("sosssssssss",current_user_db.toString());

                    finish();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "The email already registered!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                finish();
                break;
            case R.id.bt_signup:
                registerUser();
                break;
            case R.id.bt_backsignup:
                finish();
                break;
        }
    }
}