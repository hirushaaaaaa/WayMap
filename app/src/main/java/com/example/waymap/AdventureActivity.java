package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;

public class AdventureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure);

        // Initialize the ViewFlipper
        TextView exploreText = findViewById(R.id.exploretext);
        exploreText.setOnClickListener(v -> {
            Intent intent = new Intent(AdventureActivity.this, ExploreActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
