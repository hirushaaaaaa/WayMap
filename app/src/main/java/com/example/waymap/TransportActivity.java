package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TransportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);  // Replace with your layout

        // TextView references
        TextView pickMeText = findViewById(R.id.PickmeText);
        TextView ceylonText = findViewById(R.id.CeylonText);
        TextView taxiText = findViewById(R.id.TaxiText);
        TextView uberText = findViewById(R.id.ubertext);

        // PickMe click listener - Navigate to PickMeActivity
        pickMeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start PickMeActivity
                Intent intent = new Intent(TransportActivity.this, PickmeActivity.class);
                startActivity(intent);
            }
        });

        // Ceylon Taxi click listener - Navigate to CeylonTaxiActivity
        ceylonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CeylonTaxiActivity
                Intent intent = new Intent(TransportActivity.this, ceylonTaxiActivity.class);
                startActivity(intent);
            }
        });

        // Taxi.lk click listener - Navigate to TaxiActivity
        taxiText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start TaxiActivity
                Intent intent = new Intent(TransportActivity.this, TaxiActivity.class);
                startActivity(intent);
            }
        });

        // Uber click listener - Navigate to UberActivity
        uberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start UberActivity
                Intent intent = new Intent(TransportActivity.this, UberActivity.class);
                startActivity(intent);
            }
        });
    }
}
