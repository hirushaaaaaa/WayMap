package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EllaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ella);

        // Enable the back button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Back Button Listener
        Button backButton2 = findViewById(R.id.backButton2);
        backButton2.setOnClickListener(v -> {
            Intent intent = new Intent(EllaActivity.this, ScenicstopsActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Navigate to ScenicstopsActivity when back button is clicked
            Intent intent = new Intent(EllaActivity.this, ScenicstopsActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
