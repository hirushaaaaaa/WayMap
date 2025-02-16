package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RuwanmeliseyaActivity extends AppCompatActivity {

    private EditText ruwanmelitext; // Change from TextView to EditText
    private TextView locruwanmeliseya;
    private Button backButton5, editButton, saveButton;

    private boolean isAdmin = false; // Declare once globally

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruwanmeliseya);

        // Initialize Views
        ruwanmelitext = findViewById(R.id.ruwanmelitext); // Now an EditText
        locruwanmeliseya = findViewById(R.id.locruwanmeliseya);
        backButton5 = findViewById(R.id.backButton5);
        editButton = findViewById(R.id.editButton);
        saveButton = findViewById(R.id.saveButton);

        // Check if the user is an admin
        checkAdminPermissions();

        // Load the content from Firebase when the activity is created
        loadContent();

        // Set up the back button functionality
        backButton5.setOnClickListener(v -> finish()); // Close the activity (back action)

        // Set up the edit button functionality
        editButton.setOnClickListener(v -> {
            ruwanmelitext.setEnabled(true); // Enable editing
            ruwanmelitext.requestFocus(); // Focus on the EditText
        });

        // Set up the save button functionality
        saveButton.setOnClickListener(v -> {
            String content = ruwanmelitext.getText().toString();
            saveContent(content);
            ruwanmelitext.setEnabled(false); // Disable editing after saving
        });

        // Set up the location button functionality
        locruwanmeliseya.setOnClickListener(v -> {
            openLocationInGoogleMaps();
        });
    }

    private void openLocationInGoogleMaps() {
        // Coordinates for Ruwanmeliseya (example: 8.3473° N, 80.3880° E)
        String geoUri = "geo:8.3473,80.3880?q=Ruwanwelisaya, Anuradhapura, Sri Lanka";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
        intent.setPackage("com.google.android.apps.maps"); // Open directly in Google Maps app

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAdminPermissions() {
        // Retrieve admin status from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        // Debug: Check if the admin status is retrieved correctly
        Log.d("DEBUG", "Admin Status: " + isAdmin);

        // Set the visibility of buttons based on admin status
        if (isAdmin) {
            editButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);
        } else {
            editButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
        }
    }

    private void saveContent(String content) {
        // Save the content to Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ruwanmeliseya_content");
        databaseReference.setValue(content)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Content Saved!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to Save Content", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadContent() {
        // Load the content from Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ruwanmeliseya_content");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String savedContent = snapshot.getValue(String.class);
                if (savedContent != null) {
                    ruwanmelitext.setText(savedContent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RuwanmeliseyaActivity.this, "Failed to Load Content", Toast.LENGTH_SHORT).show();
            }
        });
    }
}