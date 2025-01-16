package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CultureActivity extends AppCompatActivity {

    private TextView mahanuwaratext;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture);

        // Initialize the TextView for Mahanuwara
        mahanuwaratext = findViewById(R.id.mahanuwaratext);

        // Set the OnClickListener for Mahanuwaratext
        mahanuwaratext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMahanuwaraInGoogleMaps();
            }
        });
    }

    // Method to open Mahanuwara location in Google Maps
    private void openMahanuwaraInGoogleMaps() {
        // Location coordinates for Mahanuwara (Kandy), Sri Lanka
        String uri = "geo:7.2906,80.6337?q=7.2906,80.6337(Mahanuwara)";

        // Create an Intent to launch Google Maps
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps"); // Explicitly use Google Maps app

        // Check if the Maps app is available
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent); // Open Google Maps
        } else {
            // Optionally handle the case where Google Maps is not installed
            // You can show a message or fall back to a browser
        }
    }
}
