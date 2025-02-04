package com.example.waymap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class DistanceActivity extends AppCompatActivity {

    private EditText inputKilometers;
    private EditText inputMiles;
    private EditText inputLKR;
    private Spinner currencySpinner;
    private TextView resultText;
    private final Map<String, Double> conversionRates = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distance_calculator);

        // Initialize conversion rates
        setupConversionRates();

        // Initialize UI elements
        initializeViews();
        setupSpinner();
        setupClickListeners();
    }

    private void setupConversionRates() {
        conversionRates.put("USD", 0.0031);
        conversionRates.put("EUR", 0.0028);
        conversionRates.put("GBP", 0.0024);
        conversionRates.put("AUD", 0.0047);
        conversionRates.put("JPY", 0.46);
    }

    private void initializeViews() {
        inputKilometers = findViewById(R.id.inputKilometers);
        inputMiles = findViewById(R.id.inputMiles);
        inputLKR = findViewById(R.id.inputLKR);
        currencySpinner = findViewById(R.id.currencySpinner);
        resultText = findViewById(R.id.resultText);
        Button calculateButton = findViewById(R.id.calculateButton);
        Button calculateCurrencyButton = findViewById(R.id.calculateCurrencyButton);
        TextView backButton = findViewById(R.id.backButton);
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                conversionRates.keySet().toArray(new String[0])
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
    }

    private void setupClickListeners() {
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        findViewById(R.id.calculateButton).setOnClickListener(v -> calculateDistance());
        findViewById(R.id.calculateCurrencyButton).setOnClickListener(v -> calculateCurrency());
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

    @SuppressLint("DefaultLocale")
    private void calculateCurrency() {
        String lkrText = inputLKR.getText().toString();

        if (!TextUtils.isEmpty(lkrText)) {
            double lkr = Double.parseDouble(lkrText);
            String selectedCurrency = currencySpinner.getSelectedItem().toString();
            double rate = conversionRates.get(selectedCurrency);
            double result = lkr * rate;

            resultText.setText(String.format("%.2f LKR = %.2f %s", lkr, result, selectedCurrency));
        } else {
            Toast.makeText(this, "Please enter LKR amount", Toast.LENGTH_SHORT).show();
        }
    }
}