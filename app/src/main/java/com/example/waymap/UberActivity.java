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

public class UberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uber);  // Replace with your layout

        // TextView references
        TextView uberWebLink = findViewById(R.id.textView15);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView uberLocationText = findViewById(R.id.locubertext);

        // Click listener for the Uber website link
        uberWebLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Uber's website in the browser
                String url = "https://www.uber.com";
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(webIntent);
            }
        });

        // Click listener for Uber's main branch location
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView locationTextView = findViewById(R.id.locubertext);
        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Google Maps with the Uber main branch location
                Uri locationUri = Uri.parse("geo:0,0?q=Uber+Sri+Lanka+Colombo");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

                // Optional: Show a toast message when clicked
                Toast.makeText(UberActivity.this, "Opening PickMe Main Branch Location", Toast.LENGTH_SHORT).show();
            }
        });

        // Back button functionality
        Button backButton = findViewById(R.id.uberback);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Close the current activity
            }
        });
    }
}
