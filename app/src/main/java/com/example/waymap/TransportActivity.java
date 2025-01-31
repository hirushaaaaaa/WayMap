package com.example.waymap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;  // Add Log class for debugging
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TransportActivity extends AppCompatActivity {

    private ImageView addd; // Reference to the 'addd' ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);

        // Initialize ImageView
        addd = findViewById(R.id.addd);

        // Check if admin is logged in and show/hide 'addd' ImageView
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false); // Retrieve admin status
        // Debugging: Log the value of 'isAdminLoggedIn'
        Log.d("DEBUG", "Admin Status: " + isAdmin);

        if (isAdmin) {
            Log.d("DEBUG", "Admin ImageView should be VISIBLE.");
            addd.setVisibility(View.VISIBLE);
        } else {
            Log.d("DEBUG", "Admin ImageView should be GONE.");
            addd.setVisibility(View.GONE);
        }

        // TextView references
        TextView pickMeText = findViewById(R.id.PickmeText);
        TextView ceylonText = findViewById(R.id.CeylonText);
        TextView taxiText = findViewById(R.id.TaxiText);
        TextView uberText = findViewById(R.id.ubertext);

        // PickMe click listener - Navigate to PickMeActivity
        pickMeText.setOnClickListener(v -> {
            Intent intent = new Intent(TransportActivity.this, PickmeActivity.class);
            startActivity(intent);
        });

        // Ceylon Taxi click listener - Navigate to CeylonTaxiActivity
        ceylonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportActivity.this, ceylonTaxiActivity.class);
                startActivity(intent);
            }
        });

        // Taxi.lk click listener - Navigate to TaxiActivity
        taxiText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportActivity.this, TaxiActivity.class);
                startActivity(intent);
            }
        });

        // Uber click listener - Navigate to UberActivity
        uberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportActivity.this, UberActivity.class);
                startActivity(intent);
            }
        });
    }
}
