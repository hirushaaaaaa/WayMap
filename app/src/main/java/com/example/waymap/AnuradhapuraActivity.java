package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AnuradhapuraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinharaja);

        // Enable the back button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Back Button Listener
        Button backButton1 = findViewById(R.id.backButton1);
        if (backButton1 != null) { // Ensure the button is found
            backButton1.setOnClickListener(v -> {
                Intent intent = new Intent(AnuradhapuraActivity.this, ScenicstopsActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Navigate to ScenicstopsActivity when back button is clicked
            Intent intent = new Intent(AnuradhapuraActivity.this, ScenicstopsActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
