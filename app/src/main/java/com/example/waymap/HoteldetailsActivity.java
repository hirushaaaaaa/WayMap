package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HoteldetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoteldetails); // Make sure this matches your XML file name

        // Kingsbury Hotel
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView kingsburyLoc = findViewById(R.id.kingsburyloc);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView kingsburyWeb = findViewById(R.id.kingsburyweb);

        // Hilton Hotel
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView hiltonLoc = findViewById(R.id.hiltonloc);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView hiltonWeb = findViewById(R.id.hiltonweb);

        // Jetwing Hotel
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView jetwingLoc = findViewById(R.id.jetwingloc);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView jetwingWeb = findViewById(R.id.jetwingweb);

        // Shangri-La Hotel
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView shangrilaLoc = findViewById(R.id.shangrilaaloc);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView shangrilaWeb = findViewById(R.id.shangrilaaweb);

        // Kingsbury Location Click - Open in Google Maps
        kingsburyLoc.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:6.9319,79.8436?q=The Kingsbury, Colombo");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        // Kingsbury Website Click - Open in Browser
        kingsburyWeb.setOnClickListener(v -> {
            Uri webpage = Uri.parse("https://www.thekingsburyhotel.com/");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(webIntent);
        });

        // Hilton Location Click - Open in Google Maps
        hiltonLoc.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:6.9316,79.8423?q=Hilton Colombo");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        // Hilton Website Click - Open in Browser
        hiltonWeb.setOnClickListener(v -> {
            Uri webpage = Uri.parse("https://www.hiltoncolombo1.com/");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(webIntent);
        });

        // Jetwing Location Click - Open in Google Maps
        jetwingLoc.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:6.9315,79.8553?q=Jetwing Colombo");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        // Jetwing Website Click - Open in Browser
        jetwingWeb.setOnClickListener(v -> {
            Uri webpage = Uri.parse("https://www.jetwinghotels.com/");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(webIntent);
        });

        // Shangri-La Location Click - Open in Google Maps
        shangrilaLoc.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:6.9261,79.8553?q=Shangri-La Colombo");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        // Shangri-La Website Click - Open in Browser
        shangrilaWeb.setOnClickListener(v -> {
            Uri webpage = Uri.parse("https://www.shangri-la.com/colombo/");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(webIntent);
        });
    }
}
