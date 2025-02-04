package com.example.waymap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class sinharajaActivity extends AppCompatActivity {

    private Button backButton, editButton, saveButton;
    private TextView isurumunitext, locisurumunuiya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinharaja); // Replace with your actual layout name
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isAdmin", true);  // Make sure this is true for testing
        editor.apply();
        // Initialize Views
        backButton = findViewById(R.id.backButton1);
        editButton = findViewById(R.id.editbutton);

        isurumunitext = findViewById(R.id.isurumunitext);
        locisurumunuiya = findViewById(R.id.locisurumunuiya);

        // Initially, make sure editButton is invisible for all users
        editButton.setVisibility(View.INVISIBLE);

        // Handle Back Button click
        backButton.setOnClickListener(v -> finish()); // Finish the current activity to go back

        // Handle Edit Button click
        editButton.setOnClickListener(v -> {
            // Show the text for editing and the Save button
            isurumunitext.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);  // Show the Save button
            editButton.setVisibility(View.INVISIBLE);  // Hide the Edit button
        });

        // Handle Save Button click
      
      
      
      
      
      
      

        // Check if the user is an admin
        checkAdminStatus();
    }

    private void checkAdminStatus() {
        // Check if the user is logged in as an admin using SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false); // Default is false if not found

        // Debugging: Log or print to see the value of isAdmin
        System.out.println("Admin Status: " + isAdmin);

        // If the user is an admin, show the Edit button
        if (isAdmin) {
            editButton.setVisibility(View.VISIBLE);  // Make the Edit button visible for admins
        }
    }
}
