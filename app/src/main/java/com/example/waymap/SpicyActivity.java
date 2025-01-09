package com.example.waymap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SpicyActivity extends AppCompatActivity {

    // Declare views
    private Button backButton;
    private TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spicy); // Link to your layout XML file

        // Initialize views
        backButton = findViewById(R.id.mildback); // Replace R.id.mildback with your Back Button's ID
        headerText = findViewById(R.id.textView10); // Replace R.id.textView10 with your TextView's ID

        // Set up the Back button click listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a Toast message
                Toast.makeText(SpicyActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();

                // Close the current activity
                finish();
            }
        });

        // Example: Update the header text programmatically
        headerText.setText("Welcome to Mild Spicy!");
    }
}
