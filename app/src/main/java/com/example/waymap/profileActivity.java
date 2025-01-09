package com.example.waymap;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class profileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Back Button
        Button backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener(v -> {
            // Navigate back to the previous screen
            finish();
        });

        // AutoCompleteTextViews
        findViewById(R.id.autoCompleteTextView);
        findViewById(R.id.autoCompleteTextView2);

        // More Info TextView
        TextView moreInfoText = findViewById(R.id.morei);
        moreInfoText.setOnClickListener(v -> {
            // Navigate to a "More Info" page if applicable
        });

        // Social Media Icons
        ImageView facebookIcon = findViewById(R.id.fbi);
        facebookIcon.setOnClickListener(v -> {
            // Add functionality for Facebook Icon
            // Example: Open Facebook profile
        });

        ImageView gmailIcon = findViewById(R.id.gi);
        gmailIcon.setOnClickListener(v -> {
            // Add functionality for Gmail Icon
            // Example: Compose an email
        });

        ImageView googleIcon = findViewById(R.id.googlei);
        googleIcon.setOnClickListener(v -> {
            // Add functionality for Google Icon
        });

        ImageView instagramIcon = findViewById(R.id.instai);
        instagramIcon.setOnClickListener(v -> {
            // Add functionality for Instagram Icon
        });

        ImageView twitterIcon = findViewById(R.id.twii);
        twitterIcon.setOnClickListener(v -> {
            // Add functionality for Twitter Icon
        });
    }
}
