package com.example.waymap;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HotActivity extends AppCompatActivity {

    private Button backButton;
    private TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot);

        // Initialize views
        backButton = findViewById(R.id.mildback);
        headerText = findViewById(R.id.textView10);

        // Set header text
        headerText.setText("Explore Spicy Foods!");

        // Set an onClickListener for the Back button
        backButton.setOnClickListener(v -> {
            Toast.makeText(HotActivity.this, "Returning to previous screen", Toast.LENGTH_SHORT).show();
            finish(); // Closes the current activity and returns to the previous screen
        });


    }
}
