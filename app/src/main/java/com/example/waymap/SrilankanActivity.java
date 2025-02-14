package com.example.waymap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class SrilankanActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srilankanairline);

        // Initialize ViewFlipper
        viewFlipper = findViewById(R.id.viewFlipper);
        viewFlipper.setFlipInterval(2000); // Set flip interval to 2 seconds
        viewFlipper.setAutoStart(true);

        // Find the TextView with ID "book" and set up click listener
        TextView bookTextView = findViewById(R.id.book);
        bookTextView.setOnClickListener(view -> {
            // Define the URL you want to open
            String url = "https://www.srilankan.com/en/booking"; // Replace with the actual booking URL
            openWebsite(url);
        });
    }

    // Method to open a URL in the browser
    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
