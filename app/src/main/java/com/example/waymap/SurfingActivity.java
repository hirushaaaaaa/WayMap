package com.example.waymap;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SurfingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surfing);

        TextView bayText = findViewById(R.id.baytext);
        TextView misurfingText = findViewById(R.id.misurfingtext);

        // Set click listener for "baytext"
        bayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Open Google Maps with Arugam Bay location
                    Uri locationUri = Uri.parse("geo:0,0?q=Arugam+Bay+Sri+Lanka");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
                    mapIntent.setPackage("com.google.android.apps.maps"); // Ensure it opens in Google Maps
                    startActivity(mapIntent);
                } catch (ActivityNotFoundException e) {
                    // Google Maps is not installed
                    Toast.makeText(SurfingActivity.this, "Error: Google Maps is not installed on your device.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Set click listener for "misurfingtext"
        misurfingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Open Google Maps with Mirissa location
                    Uri locationUri = Uri.parse("geo:0,0?q=Mirissa+Sri+Lanka");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
                    mapIntent.setPackage("com.google.android.apps.maps"); // Ensure it opens in Google Maps
                    startActivity(mapIntent);
                } catch (ActivityNotFoundException e) {
                    // Google Maps is not installed
                    Toast.makeText(SurfingActivity.this, "Error: Google Maps is not installed on your device.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
