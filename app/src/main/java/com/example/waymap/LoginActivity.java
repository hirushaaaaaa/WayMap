package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

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
                Toast.makeText(LoginActivity.this, "Admin login successful", Toast.LENGTH_SHORT).show();
                navigateToHomePage();
                return;
            }

            // Validate user credentials from Firebase
            validateUser(username, password);
        });
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
}
