package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ExploreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure_explore);

        // Find the button by its ID
        Button safariButton = findViewById(R.id.safaributton);

        // Set an OnClickListener on the button
        safariButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to SafariActivity
                Intent intent = new Intent(ExploreActivity.this, SafariActivity.class);
                startActivity(intent);
            }
        });
    }
}
