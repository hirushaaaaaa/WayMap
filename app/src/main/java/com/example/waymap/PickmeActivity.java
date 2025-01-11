package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;

public class PickmeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickme);  // Use your layout XML file name here

        // Set up the TextView for clickable link (PickMe Website)
        TextView websiteTextView = findViewById(R.id.textView15);
        websiteTextView.setMovementMethod(LinkMovementMethod.getInstance()); // Make the text clickable

        // Set up the Back button functionality
        Button backButton = findViewById(R.id.pickback);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                onBackPressed();  // This handles going back to the previous screen
            }
        });

        // Set up the website TextView click listener
        websiteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the PickMe website when clicked
                Uri uri = Uri.parse("http://www.pickme.lk");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                // Optional: Show a toast message when clicked
                Toast.makeText(PickmeActivity.this, "Opening PickMe Website", Toast.LENGTH_SHORT).show();
            }
        });

        // Add a new TextView for the PickMe Main Branch location
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView locationTextView = findViewById(R.id.locpickme);
        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Google Maps with the PickMe main branch location
                Uri locationUri = Uri.parse("geo:0,0?q=PickMe Main Branch, Colombo, Sri Lanka");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

                // Optional: Show a toast message when clicked
                Toast.makeText(PickmeActivity.this, "Opening PickMe Main Branch Location", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
