package com.example.waymap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DistanceActivity extends AppCompatActivity {

    private EditText inputKilometers;
    private EditText inputMiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distance_calculator);

        // Initialize UI elements
        inputKilometers = findViewById(R.id.inputKilometers);
        inputMiles = findViewById(R.id.inputMiles);
        Button calculateButton = findViewById(R.id.calculateButton);
        TextView backButton = findViewById(R.id.backButton);

        // Back Button Listener
        backButton.setOnClickListener(v -> finish());

        // Calculate Button Listener
        calculateButton.setOnClickListener(v -> calculateDistance());
    }

    @SuppressLint("DefaultLocale")
    private void calculateDistance() {
        String kmText = inputKilometers.getText().toString();
        String milesText = inputMiles.getText().toString();

        if (!TextUtils.isEmpty(kmText) && TextUtils.isEmpty(milesText)) {
            double kilometers = Double.parseDouble(kmText);
            double miles = kilometers * 0.621371;
            inputMiles.setText(String.format("%.2f", miles));
        } else if (!TextUtils.isEmpty(milesText) && TextUtils.isEmpty(kmText)) {
            double miles = Double.parseDouble(milesText);
            double kilometers = miles / 0.621371;
            inputKilometers.setText(String.format("%.2f", kilometers));
        } else {
            Toast.makeText(this, "Please fill only one field", Toast.LENGTH_SHORT).show();
        }
    }
}
