package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("LoginActivity", "Activity created");

        // Initialize the views
        EditText etUsername = findViewById(R.id.editTextText);  // Ensure this ID matches your XML
        EditText etPassword = findViewById(R.id.editTextTextPassword);  // Ensure this ID matches your XML
        Button btnLogin = findViewById(R.id.loginbutton);
        Button btnBackToWelcome = findViewById(R.id.welcomebutton);

        // Check if user is already logged in
        if (isUserLoggedIn()) {
            navigateToHomePage();
            return;
        }

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
                // Show error message if fields are empty
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check credentials: username = "admin" and password = "1111"
            if (username.equals("admin") && password.equals("1111")) {
                // Show success message
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                // Navigate to Home Page
                navigateToHomePage();
            } else {
                // Show error message for incorrect credentials
                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to check if the user is already logged in
    private boolean isUserLoggedIn() {
        // Implement logic to check if the user is already logged in
        // This could be a shared preference or a check in a database or session
        return false; // Placeholder for actual implementation
    }

    // Method to navigate to Home Page
    private void navigateToHomePage() {
        Intent intent = new Intent(LoginActivity.this, homeActivity.class); // Corrected to HomeActivity
        startActivity(intent);
        finish(); // Finish the LoginActivity so that it won't return to the login screen
    }
}
