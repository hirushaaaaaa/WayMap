package com.example.waymap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MildActivity extends AppCompatActivity {

    private Button backButton;
    private TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mild); // Use your XML layout file name here

        // Initialize views
        backButton = findViewById(R.id.mildback);
        headerText = findViewById(R.id.textView10);


        // Set an onClickListener for the Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MildActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
                finish(); // Closes the current activity
            }
        });

        // Example: Updating the header text programmatically
        headerText.setText("Welcome to Mild Spicy!");


    }
}
