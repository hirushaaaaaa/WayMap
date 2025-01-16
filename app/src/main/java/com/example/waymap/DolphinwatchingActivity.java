package com.example.waymap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DolphinwatchingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dolphinwatching); // Update with your actual XML file name

        // Find the TextViews by ID
        TextView trincotext = findViewById(R.id.trincotext);
        TextView alankudatext = findViewById(R.id.Alankudatext);

        // Set onClickListener for Trincomalee TextView
        trincotext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Trincomalee location in Google Maps
                String trincoLocation = "geo:8.5690,81.2335?q=Trincomalee";
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trincoLocation));
                mapIntent.setPackage("com.google.android.apps.maps"); // Ensures Google Maps is used
                startActivity(mapIntent);
            }
        });

        // Set onClickListener for Alankuda Beach TextView
        alankudatext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Alankuda Beach location in Google Maps
                String alankudaLocation = "geo:8.0305,79.7035?q=Alankuda+Beach";
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(alankudaLocation));
                mapIntent.setPackage("com.google.android.apps.maps"); // Ensures Google Maps is used
                startActivity(mapIntent);
            }
        });
    }
}
