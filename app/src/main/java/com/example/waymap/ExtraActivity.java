package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ExtraActivity extends AppCompatActivity {

    // Declare the Back button
    private Button mildBackButton;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        // Initialize the Back button
        mildBackButton = findViewById(R.id.mildback);
        ImageView adminImageView = findViewById(R.id.add3);

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
