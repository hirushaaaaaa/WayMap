package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FuelManagementActivity extends AppCompatActivity {
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuelmanagement);

        // Bind UI components
        EditText fuelConsumptionInput = findViewById(R.id.editFuelConsumption);
        EditText distanceInput = findViewById(R.id.editDistance);
        EditText fuelRateInput = findViewById(R.id.editFuelRate);
        Button calculateButton = findViewById(R.id.calculateButton);
        TextView resultText = findViewById(R.id.resultText);
        Button backbutton =findViewById(R.id.backbuttonf);
        // Set OnClickListener for the fuel text
        backbutton.setOnClickListener(v -> {
            Intent intent = new Intent(FuelManagementActivity.this, moreActivity.class);
            startActivity(intent);
        });
        // Set click listener for the calculate button
        calculateButton.setOnClickListener(v -> {
            // Get user input
            String fuelConsumptionStr = fuelConsumptionInput.getText().toString().trim();
            String distanceStr = distanceInput.getText().toString().trim();
            String fuelRateStr = fuelRateInput.getText().toString().trim();

            // Check if any input field is empty
            if (fuelConsumptionStr.isEmpty() || distanceStr.isEmpty() || fuelRateStr.isEmpty()) {
                Toast.makeText(FuelManagementActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // Parse input values
                double fuelConsumption = Double.parseDouble(fuelConsumptionStr);
                double distance = Double.parseDouble(distanceStr);
                double fuelRate = Double.parseDouble(fuelRateStr);

                // Calculate total fuel cost
                double totalCost = (distance / 100) * fuelConsumption * fuelRate;

                // Display result
                resultText.setText(String.format("Total Fuel Cost: %.2f", totalCost));
            } catch (NumberFormatException e) {
                // Handle invalid numeric input
                Toast.makeText(FuelManagementActivity.this, "Enter valid numbers", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
