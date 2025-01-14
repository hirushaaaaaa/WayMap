package com.example.waymap;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SafariActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safari);

        // Wilpattu National Park Button
        TextView safariLocButton = findViewById(R.id.wilpattutext);
        safariLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Open Google Maps with Wilpattu National Park location
                    Uri locationUri = Uri.parse("geo:0,0?q=Wilpattu+National+Park+Sri+Lanka");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
                    mapIntent.setPackage("com.google.android.apps.maps"); // Ensure it opens in Google Maps
                    startActivity(mapIntent);
                } catch (ActivityNotFoundException e) {
                    // Google Maps is not installed
                    Toast.makeText(SafariActivity.this, "Error: Google Maps is not installed on your device.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Sinharaja Rainforest Button
        TextView sinhaLocButton = findViewById(R.id.sinhatext);
        sinhaLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Open Google Maps with Sinharaja Rainforest location
                    Uri locationUri = Uri.parse("geo:0,0?q=Sinharaja+Rainforest+Sri+Lanka");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
                    mapIntent.setPackage("com.google.android.apps.maps"); // Ensure it opens in Google Maps
                    startActivity(mapIntent);
                } catch (ActivityNotFoundException e) {
                    // Google Maps is not installed
                    Toast.makeText(SafariActivity.this, "Error: Google Maps is not installed on your device.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
