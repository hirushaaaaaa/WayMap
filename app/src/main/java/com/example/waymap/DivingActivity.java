package com.example.waymap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DivingActivity extends AppCompatActivity {

    private TextView kalpitiyaText, unawatunaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divingandsnorkeling);

        // Initialize TextViews
        kalpitiyaText = findViewById(R.id.kalpitiyatext);
        unawatunaText = findViewById(R.id.unawatunatext);

        // Set click listener for Kalpitiya text
        kalpitiyaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleMaps("Kalpitiya, Sri Lanka");
            }
        });

        // Set click listener for Unawatuna text
        unawatunaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleMaps("Unawatuna, Sri Lanka");
            }
        });
    }

    // Method to open Google Maps with the location
    private void openGoogleMaps(String location) {
        // Create an Intent to open Google Maps with the location
        Uri uri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");  // Open with Google Maps
        startActivity(intent);
    }
}
