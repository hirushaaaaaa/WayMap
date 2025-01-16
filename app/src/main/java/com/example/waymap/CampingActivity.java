package com.example.waymap;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CampingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camping);

        // Meemure Text Click Event
        TextView meemureText = findViewById(R.id.meemuretext);
        meemureText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLocation("Meemure+Sri+Lanka");
            }
        });

        // Blue Beach Text Click Event
        TextView blueBeachText = findViewById(R.id.bluebeachtext);
        blueBeachText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLocation("Blue+Beach+Island+Sri+Lanka");
            }
        });
    }

    /**
     * Opens the Google Maps application to the specified location.
     *
     * @param location The location to open in Google Maps.
     */
    private void openLocation(String location) {
        try {
            // Create a URI for the location
            Uri locationUri = Uri.parse("geo:0,0?q=" + location);
            // Create an Intent to open Google Maps
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
            mapIntent.setPackage("com.google.android.apps.maps"); // Ensure Google Maps is used
            startActivity(mapIntent);
        } catch (ActivityNotFoundException e) {
            // Google Maps app is not installed
            Toast.makeText(this, "Error: Google Maps is not installed on your device.", Toast.LENGTH_LONG).show();
        }
    }
}
