package com.example.waymap;

import android.os.Bundle;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class SrilankanActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srilankanairline);

        // Initialize ViewFlipper
        viewFlipper = findViewById(R.id.viewFlipper);
        viewFlipper.setFlipInterval(2000); // Set flip interval to 2 seconds
        viewFlipper.setAutoStart(true);
    }
}
