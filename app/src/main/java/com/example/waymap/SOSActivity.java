package com.example.waymap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SOSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        // Set up button click listeners
        findViewById(R.id.button_police).setOnClickListener(v -> dialNumber("119"));
        findViewById(R.id.button_emergency).setOnClickListener(v -> dialNumber("118"));
        findViewById(R.id.button_disaster).setOnClickListener(v -> dialNumber("990"));
        findViewById(R.id.suwaseriya).setOnClickListener(v -> dialNumber("1990"));
    }

    private void dialNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }
}
