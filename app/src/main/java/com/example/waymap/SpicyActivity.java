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
        setContentView(R.layout.activity_spicy);
        // Initialize views
        backButton = findViewById(R.id.mildback);
        headerText = findViewById(R.id.textView10);

        // Set up the Back button click listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a Toast message
                Toast.makeText(SpicyActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();


                finish();
            }
        });


        headerText.setText("Welcome to Mild Spicy!");
    }
}
