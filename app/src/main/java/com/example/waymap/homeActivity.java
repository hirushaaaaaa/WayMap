package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the ImageView and TextView for Distance Calculator
        ImageView distanceCalculatorImage = findViewById(R.id.Imagebutton);
        TextView distanceCalculatorText = findViewById(R.id.textbutton);

        // Set OnClickListener for the ImageView
        distanceCalculatorImage.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, DistanceActivity.class);
            startActivity(intent);
        });

        // Set OnClickListener for the TextView
        distanceCalculatorText.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, DistanceActivity.class);
            startActivity(intent);
        });
    }
}
