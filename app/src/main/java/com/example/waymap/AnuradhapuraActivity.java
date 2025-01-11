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

public class AnuradhapuraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinharaja); // Ensure this is the correct layout

        // Enable the back button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Back Button Listener
        Button backButton1 = findViewById(R.id.backButton1);
        if (backButton1 != null) {
            backButton1.setOnClickListener(v -> {
                Intent intent = new Intent(AnuradhapuraActivity.this, ScenicstopsActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            });
        }

        // Location text view click listener for Isurumuniya
        TextView locIsurumuniya = findViewById(R.id.locisurumunuiya); // Ensure this is the correct ID in your layout
        if (locIsurumuniya != null) {
            locIsurumuniya.setOnClickListener(v -> {
                // Define the location of Isurumuniya (you can replace this with the exact coordinates if needed)
                String location = "https://www.google.com/maps?q=Isurumuniya,Anuradhapura,Sri+Lanka";
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(location));
                mapIntent.setPackage("com.google.android.apps.maps"); // Ensure we use Google Maps

                // Check if Google Maps is installed
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    // If Google Maps is not installed, show a toast or handle accordingly
                    Toast.makeText(AnuradhapuraActivity.this, "Google Maps is not installed.", Toast.LENGTH_SHORT).show();

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
            // Navigate to ScenicstopsActivity when the back button is clicked
            Intent intent = new Intent(AnuradhapuraActivity.this, ScenicstopsActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
