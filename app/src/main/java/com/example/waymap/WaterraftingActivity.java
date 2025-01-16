package com.example.waymap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WaterraftingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterrafting);

        // Get references to the TextViews
        TextView kithulgalaText = findViewById(R.id.kithulgalatext);
        TextView borderlandText = findViewById(R.id.Borderlandtext);

        // Set click listener for Kithulgala
        kithulgalaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLocationInGoogleMaps("Kitulgala, Sri Lanka");
            }
        });

        // Set click listener for Borderlands Sri Lanka
        borderlandText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLocationInGoogleMaps("Borderlands Sri Lanka");
            }
        });
    }

    /**
     * Opens a specified location in Google Maps.
     *
     * @param location The name or address of the location to open.
     */
    private void openLocationInGoogleMaps(String location) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        // Check if Google Maps is available
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Handle the case when Google Maps is not available
            // You can show a toast or dialog here
        }
    }
}
