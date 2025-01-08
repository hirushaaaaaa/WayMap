package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class moreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        // Initialize views
        ImageView fuelImageButton = findViewById(R.id.fimagebutton);
        TextView fuelText = findViewById(R.id.ftext);
        TextView backbutton = findViewById(R.id.backk);
        ImageView foodimageButton = findViewById(R.id.foodimage);
        TextView foodText = findViewById(R.id.foodtext);

        // Set OnClickListener for the back button
        backbutton.setOnClickListener(v -> {
            Intent intent = new Intent(moreActivity.this, homeActivity.class);
            startActivity(intent);
        });

        // Set OnClickListener for the fuel image
        fuelImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(moreActivity.this, FuelManagementActivity.class);
            startActivity(intent);
        });

        // Set OnClickListener for the fuel text
        fuelText.setOnClickListener(v -> {
            Intent intent = new Intent(moreActivity.this, FuelManagementActivity.class);
            startActivity(intent);
        });

        // Set OnClickListener for the local food image
        foodimageButton.setOnClickListener(v -> {
            Intent intent = new Intent(moreActivity.this, LocaldishesActivity.class);
            startActivity(intent);
        });

        // Set OnClickListener for the local food text
        foodText.setOnClickListener(v -> {
            Intent intent = new Intent(moreActivity.this, LocaldishesActivity.class);
            startActivity(intent);
        });
    }
}
