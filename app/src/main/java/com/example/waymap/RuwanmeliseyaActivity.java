package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RuwanmeliseyaActivity extends AppCompatActivity {

    private TextView ruwanmelitext, locruwanmeliseya;
    private ImageView imageruwanmeliseya;
    private Button backButton5, editButton, saveButton;

    private boolean isAdmin = false; // Declare once globally

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruwanmeliseya);

        // Initialize Views
        ruwanmelitext = findViewById(R.id.ruwanmelitext);
        locruwanmeliseya = findViewById(R.id.locruwanmeliseya);
        imageruwanmeliseya = findViewById(R.id.imageruwanmeliseya);
        backButton5 = findViewById(R.id.backButton5);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false); // Retrieve admin status

// Debug: Check if the admin status is retrieved correctly
        Log.d("DEBUG", "Admin Status: " + isAdmin);

// Set the visibility of buttons based on admin status
        if (isAdmin) {
            // Admin users can see both edit and save buttons
            editButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);
        } else {
            // Regular users cannot see these buttons
            editButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
        }
        checkAdminPermissions();

        // Set up the back button functionality
        backButton5.setOnClickListener(v -> finish()); // Close the activity (back action)

        // Set up the edit button functionality
        editButton.setOnClickListener(v -> ruwanmelitext.setText("Edit your content here..."));

        // Set up the save button functionality
        saveButton.setOnClickListener(v -> {
            String content = ruwanmelitext.getText().toString();
            saveContent(content);
        });
    }

    private void checkAdminPermissions() {
        if (isAdmin) {
            editButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);
        } else {
            editButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
        }
    }

    private void saveContent(String content) {
        Toast.makeText(this, "Content Saved!", Toast.LENGTH_SHORT).show();
    }
}
