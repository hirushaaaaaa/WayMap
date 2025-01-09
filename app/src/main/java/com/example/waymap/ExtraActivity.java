package com.example.waymap; // Replace with your package name

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ExtraActivity extends AppCompatActivity {

    // Declare the Back button
    private Button mildBackButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra); // Replace with your actual layout file

        // Initialize the Back button
        mildBackButton = findViewById(R.id.mildback);

        // Set onClickListener for the Back button
        mildBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back button click (for example, go back to previous activity)
                onBackPressed(); // This will take you to the previous screen/activity
            }
        });
    }
}
