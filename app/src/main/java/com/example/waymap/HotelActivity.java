package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class HotelActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        // Find the Explore button
        View exploreButton = findViewById(R.id.exploretext2);

        // Set click listener to navigate to HoteldetailsActivity
        exploreButton.setOnClickListener(v -> {
            Intent intent = new Intent(HotelActivity.this, HoteldetailsActivity.class);
            startActivity(intent);
        });
    }
}
