package com.example.waymap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MirissaActivity extends AppCompatActivity {
    private boolean isAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirissa);

        // Enable the back button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Back Button Listener
        Button backButton = findViewById(R.id.backButton4);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(MirissaActivity.this, ScenicstopsActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        });

        // Open Mirissa location in Google Maps when locmirissa TextView is clicked
        TextView locmirissaTextView = findViewById(R.id.locmirissa);
        locmirissaTextView.setOnClickListener(v -> {
            // Mirissa's location query
            String geoUri = "geo:0,0?q=Mirissa"; // Replace with coordinates if needed
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            mapIntent.setPackage("com.google.android.apps.maps"); // Use Google Maps
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // Fallback if Google Maps is not installed
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri)));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Navigate to ScenicstopsActivity when back button is clicked
            Intent intent = new Intent(MirissaActivity.this, ScenicstopsActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
