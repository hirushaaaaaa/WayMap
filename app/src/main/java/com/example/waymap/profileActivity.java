package com.example.waymap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class profileActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView emailTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        usernameTextView = findViewById(R.id.username_text_view);
        emailTextView = findViewById(R.id.email_text_view);

        // Get login information from SharedPreferences
        SharedPreferences loginPrefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = loginPrefs.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            String username = loginPrefs.getString("username", ""); // Get username
            String email = loginPrefs.getString("email", ""); // Get email
            boolean isAdmin = loginPrefs.getBoolean("isAdmin", false);

            // Display user information
            usernameTextView.setText(" " + username);

            if (isAdmin) {
                emailTextView.setText("Admin Account");
            } else {
                emailTextView.setText("" + email);
            }
        } else {
            // No user logged in, return to login
            Toast.makeText(this, "No user logged in!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(profileActivity.this, LoginActivity.class));
            finish();
        }
    }
}
