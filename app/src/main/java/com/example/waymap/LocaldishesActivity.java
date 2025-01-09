package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LocaldishesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localdishes);

        // Initialize buttons
        // Declare buttons
        Button backFoodButton = findViewById(R.id.backfood);
        Button mildButton = findViewById(R.id.mildbutton);
        Button spicyButton = findViewById(R.id.spicybutton);
        Button hotButton = findViewById(R.id.hotbutton);
        Button extraButton = findViewById(R.id.extrabutton);

        // Back Button Listener
        backFoodButton.setOnClickListener(v -> {
            finish();
        });

        // Mild Button Listener
        mildButton.setOnClickListener(v -> {
            // Open MildActivity
            Intent intent = new Intent(LocaldishesActivity.this, MildActivity.class);
            startActivity(intent);
        });

        // Spicy Button Listener
        spicyButton.setOnClickListener(v -> {
            // Open SpicyActivity
            Intent intent = new Intent(LocaldishesActivity.this, SpicyActivity.class);
            startActivity(intent);
        });

        // Hot Button Listener
        hotButton.setOnClickListener(v -> {
            // Open HotActivity
            Intent intent = new Intent(LocaldishesActivity.this, HotActivity.class);
            startActivity(intent);
        });

        // Extra Button Listener
        extraButton.setOnClickListener(v -> {
            // Open ExtraActivity
            Intent intent = new Intent(LocaldishesActivity.this, ExtraActivity.class);
            startActivity(intent);
        });
    }
}
