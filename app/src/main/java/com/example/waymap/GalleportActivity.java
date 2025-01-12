package com.example.waymap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GalleportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleport);

        // Enable the back button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Back Button Listener
        Button backButton3 = findViewById(R.id.backbuttonf);
        backButton3.setOnClickListener(v -> {
            Intent intent = new Intent(GalleportActivity.this, ScenicstopsActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        });

        // Locate Galle Port when clicking locgalle TextView
        TextView locgalle = findViewById(R.id.locgalle);
        locgalle.setOnClickListener(v -> {
            // Replace with Galle Port's actual coordinates or query string
            String geoUri = "geo:0,0?q=Galle Port";
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            mapIntent.setPackage("com.google.android.apps.maps"); // Ensure Google Maps is used
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // Fallback message if Google Maps is not installed
                Intent fallbackIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(fallbackIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Navigate to ScenicstopsActivity when back button is clicked
            Intent intent = new Intent(GalleportActivity.this, ScenicstopsActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
