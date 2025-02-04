package com.example.waymap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private BreakIterator etUsername;
    private BreakIterator etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Initialize views
        EditText etUsername = findViewById(R.id.editTextText);
        EditText etPassword = findViewById(R.id.editTextTextPassword);
        Button btnLogin = findViewById(R.id.loginbutton);
        Button btnBackToWelcome = findViewById(R.id.welcomebutton);

        // Back to Welcome Button Action
        btnBackToWelcome.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
            startActivity(intent);
        });

        // Login Button Action
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Basic validation for empty fields
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check for admin credentials first
            if (username.equals("admin") && password.equals("1111")) {
                saveLoginState(username, true);
                Toast.makeText(LoginActivity.this, "Admin login successful", Toast.LENGTH_SHORT).show();
                navigateToHomePage();
                return;
            }

            // Validate user credentials from Firebase
            validateUser(username, password);
        });
    }

    private void saveLoginState(String username, boolean isAdmin) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putBoolean("isAdmin", isAdmin); // Save admin status
        editor.apply();
    }

    private void validateUser(String username, String password) {
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Get user data
                        String dbPassword = snapshot.child("password").getValue(String.class);
                        if (dbPassword != null && dbPassword.equals(password)) {
                            saveLoginState(username, false); // Not an admin
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            navigateToHomePage();
                            return;
                        }
                    }
                    Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Error validating user", databaseError.toException());
                Toast.makeText(LoginActivity.this, "Failed to validate user: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToHomePage() {
        Intent intent = new Intent(LoginActivity.this, homeActivity.class);
        startActivity(intent);
        finish();
    }
    // Inside your login activity
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public void loginUser() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Navigate to the route planner activity
                        startActivity(new Intent(LoginActivity.this, RouteplannerActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}