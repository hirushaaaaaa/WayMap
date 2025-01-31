package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ExploreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure_explore);

        // Find the button by its ID
        Button safariButton = findViewById(R.id.safaributton);
        Button surfingButton = findViewById(R.id.surfingbutton);
        Button campingButton = findViewById(R.id.campingbutton);
        Button divingButton = findViewById(R.id. divingbutton);
        Button waterraftingButton = findViewById(R.id.waterrafytingbutton);
        Button cultureButton = findViewById(R.id.culturebutton);
        Button dolphinButton = findViewById(R.id.whalebutton);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView adminImageView = findViewById(R.id.adddd);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false); // Retrieve admin status

        // Debug: Check if the admin status is retrieved correctly
        Log.d("DEBUG", "Admin Status: " + isAdmin);

        if (isAdmin) {
            Log.d("DEBUG", "Admin ImageView should be VISIBLE.");
            adminImageView.setVisibility(View.VISIBLE);
        } else {
            Log.d("DEBUG", "Admin ImageView should be GONE.");
            adminImageView.setVisibility(View.GONE);
        }
        // Set an OnClickListener on the button
        safariButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to SafariActivity
                Intent intent = new Intent(ExploreActivity.this, SafariActivity.class);
                startActivity(intent);
            }
        });
        surfingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to SurfingActivity
                Intent intent = new Intent(ExploreActivity.this, SurfingActivity.class);
                startActivity(intent);
            }
        });
        campingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to SurfingActivity
                Intent intent = new Intent(ExploreActivity.this, CampingActivity.class);
                startActivity(intent);
            }
        });
        divingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to SurfingActivity
                Intent intent = new Intent(ExploreActivity.this, DivingActivity.class);
                startActivity(intent);
            }
        });
        waterraftingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to SafariActivity
                Intent intent = new Intent(ExploreActivity.this, WaterraftingActivity.class);
                startActivity(intent);
            }
        });
        cultureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to SafariActivity
                Intent intent = new Intent(ExploreActivity.this, CultureActivity.class);
                startActivity(intent);
            }
        });
        dolphinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to SafariActivity
                Intent intent = new Intent(ExploreActivity.this, DolphinwatchingActivity.class);
                startActivity(intent);
            }
        });
    }
}
