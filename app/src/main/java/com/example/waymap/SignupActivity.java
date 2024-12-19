package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity); // Updated layout reference

        // Initialize the views
        EditText etUsername = findViewById(R.id.etSignupUsername);
        EditText etEmail = findViewById(R.id.etSignupEmail);
        EditText etPassword = findViewById(R.id.etSignupPassword);
        EditText etReenterPassword = findViewById(R.id.etSignupReenterPassword);
        Button btnSignup = findViewById(R.id.btnSignup);
        Button btnBackToWelcome = findViewById(R.id.btnBackToWelcome);

        // Back to Welcome Button Action
        btnBackToWelcome.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, WelcomeActivity.class);
            startActivity(intent);
        });

        // Signup Button Action
        btnSignup.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String reenterPassword = etReenterPassword.getText().toString().trim();

            // Basic validation for empty fields
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || reenterPassword.isEmpty()) {
                Toast.makeText(SignupActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if passwords match
            if (!password.equals(reenterPassword)) {
                Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Proceed with signup logic (e.g., Firebase authentication or API call)
            // For now, we will just show a success message
            Toast.makeText(SignupActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
        });
    }
}
