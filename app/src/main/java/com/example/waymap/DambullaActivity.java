package com.example.waymap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DambullaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dambulla); // Ensure correct layout file is used

        // Enable the back button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Back Button Listener
        Button backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                Intent intent = new Intent(DambullaActivity.this, ScenicstopsActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            });
        }

        // Location text view click listener for Dambulla
        TextView locDambulla = findViewById(R.id.locdambulla); // Ensure the correct ID in your layout XML
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
