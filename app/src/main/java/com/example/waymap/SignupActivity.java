package com.example.waymap;

import static com.example.waymap.R.layout.signup_activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(signup_activity);

        // Find the "Back to Welcome" button
        Button backToWelcomeButton = findViewById(R.id.button2);

        // Set OnClickListener
        backToWelcomeButton.setOnClickListener(v -> {
            // Navigate to the Welcome page
            Intent intent = new Intent(SignupActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish(); // Optional: Close current activity to prevent returning to it
        });
    }
}
