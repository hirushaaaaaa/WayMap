package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MildActivity extends AppCompatActivity {

    private Button backButton;
    private TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mild);

        // Initialize views
        backButton = findViewById(R.id.mildback);
        headerText = findViewById(R.id.textView10);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView adminImageView = findViewById(R.id.add1);

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


        // Set an onClickListener for the Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MildActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
                finish(); // Closes the current activity
            }
        });


        headerText.setText("Welcome to Mild Spicy!");


    }
}
