package com.example.waymap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DambullaActivity extends AppCompatActivity {

    private Button backButton, editButton; // Declare editButton
    private TextView locDambulla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dambulla); // Ensure correct layout file is used
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false); // Retrieve admin status

        // Debug: Check if the admin status is retrieved correctly
        Log.d("DEBUG", "Admin Status: " + isAdmin);
        Log.d("DEBUG", "Login Status: " + sharedPreferences.getBoolean("isLoggedIn", false));
        Log.d("DEBUG", "Admin Status: " + sharedPreferences.getBoolean("isAdmin", false));

        // Initialize Views
        backButton = findViewById(R.id.backButton);
        editButton = findViewById(R.id.deditbutton); // Initialize the editButton
        locDambulla = findViewById(R.id.locdambulla); // Ensure correct ID in your layout XML

        // Initially, make the editButton invisible for all users
        editButton.setVisibility(View.INVISIBLE);
        editButton.setOnClickListener(v -> {
            if (isAdmin) {
                editButton.setVisibility(View.VISIBLE); // Show only if admin
            } else {
                editButton.setVisibility(View.GONE); // Hide for regular users
            }
        });
        // Check if the user is an admin
        checkAdminStatus();

        // Enable the back button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Back Button Listener
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                Intent intent = new Intent(DambullaActivity.this, ScenicstopsActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            });
        }

        // Location text view click listener for Dambulla
        if (locDambulla != null) {
            locDambulla.setOnClickListener(v -> {
                // Define the location of Dambulla
                String location = "https://www.google.com/maps?q=Dambulla,Sri+Lanka";
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(location));
                mapIntent.setPackage("com.google.android.apps.maps"); // Ensure we use Google Maps

                // Check if Google Maps is installed
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    // If Google Maps is not installed, show a toast or handle accordingly
                    Toast.makeText(DambullaActivity.this, "Google Maps is not installed.", Toast.LENGTH_SHORT).show();

                    // Optionally, open the location in a browser
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(location));
                    startActivity(browserIntent);
                }
            });
        }
    }

    private void checkAdminStatus() {
        // Check if the user is logged in as an admin using SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false); // Default is false if not found

        // Debugging: Log the value of isAdmin to verify the status
        System.out.println("Admin Status: " + isAdmin);

        // If the user is an admin, show the Edit button
        if (isAdmin) {
            editButton.setVisibility(View.VISIBLE); // Make the Edit button visible for admins
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Navigate to ScenicstopsActivity when back button is clicked
            Intent intent = new Intent(DambullaActivity.this, ScenicstopsActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
