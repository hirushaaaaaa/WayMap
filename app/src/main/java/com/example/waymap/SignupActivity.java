package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText, reEnterPasswordEditText;
    private Button signUpButton, backToWelcomeButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        try {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        } catch (Exception e) {
            Log.e("FirebaseError", "Failed to initialize Firebase", e);
            Toast.makeText(this, "Error initializing Firebase: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        usernameEditText = findViewById(R.id.editTextText2);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword3);
        reEnterPasswordEditText = findViewById(R.id.editTextTextPassword4);
        signUpButton = findViewById(R.id.signUpButton);
        backToWelcomeButton = findViewById(R.id.button2);

        backToWelcomeButton.setOnClickListener(v -> navigateToWelcome());

        signUpButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String reEnterPassword = reEnterPasswordEditText.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || reEnterPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(reEnterPassword)) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = databaseReference.push().getKey();
        if (userId == null) {
            Toast.makeText(this, "Failed to generate user ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(userId, username, email, password);
        databaseReference.child(userId).setValue(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "User registered successfully.", Toast.LENGTH_SHORT).show();
                    navigateToWelcome();
                })
                .addOnFailureListener(e -> {
                    Log.e("DatabaseError", "Failed to register user", e);
                    Toast.makeText(this, "Failed to register user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void navigateToWelcome() {
        Intent intent = new Intent(SignupActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    public static class User {
        public String userId;
        public String username;
        public String email;
        public String password;

        public User() { }

        public User(String userId, String username, String email, String password) {
            this.userId = userId;
            this.username = username;
            this.email = email;
            this.password = password;
        }


            // Initialize Firebase


            // Access Firebase database
             FirebaseDatabase database = FirebaseDatabase.getInstance();
        }
    }

