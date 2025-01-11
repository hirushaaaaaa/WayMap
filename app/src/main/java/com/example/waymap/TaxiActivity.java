package com.example.waymap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TaxiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxilk); // Your XML layout file name

        // Back button functionality
        Button backButton = findViewById(R.id.taxilkback);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                onBackPressed();
            }
        });

        // TextView for website link
        TextView websiteTextView = findViewById(R.id.textView15);
        websiteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Taxi.lk website
                Uri websiteUri = Uri.parse("http://www.taxi.lk");
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, websiteUri);
                startActivity(websiteIntent);

                // Optional: Show a toast message
                Toast.makeText(TaxiActivity.this, "Opening Taxi.lk Website", Toast.LENGTH_SHORT).show();
            }
        });

        // TextView for opening the main branch location in Google Maps
        TextView locationTextView = findViewById(R.id.loctaxilk);
        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Google Maps with the main branch location
                Uri locationUri = Uri.parse("geo:6.9271,79.8612?q=Taxi.lk Main Branch, Colombo, Sri Lanka");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
                mapIntent.setPackage("com.google.android.apps.maps"); // Ensure it opens in Google Maps
                startActivity(mapIntent);

                // Optional: Show a toast message
                Toast.makeText(TaxiActivity.this, "Opening Main Branch in Google Maps", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
