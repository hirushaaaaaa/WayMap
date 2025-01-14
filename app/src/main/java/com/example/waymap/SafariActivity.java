package com.example.waymap;

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

        // Yala National Park Button
        TextView safariLocButton = findViewById(R.id.wilpattutext);
        safariLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLocation("geo:0,0?q=Yala+National+Park+Sri+Lanka");
            }
        });

        // Sinharaja Rainforest Button
        TextView sinhaLocButton = findViewById(R.id.sinhatext);
        sinhaLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLocation("geo:0,0?q=Sinharaja+Rainforest+Sri+Lanka");
            }
        });
    }

    // Method to open location in Google Maps
    private void openLocation(String geoUri) {
        try {
            Uri locationUri = Uri.parse(geoUri);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                Toast.makeText(this, "Google Maps is not installed.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to open location.", Toast.LENGTH_SHORT).show();
        }
    }
}
